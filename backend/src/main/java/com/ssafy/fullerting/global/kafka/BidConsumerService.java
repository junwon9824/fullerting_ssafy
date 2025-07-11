package com.ssafy.fullerting.global.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.fullerting.alarm.service.EventAlarmService;
import com.ssafy.fullerting.bidLog.model.dto.request.BidProposeRequest;
import com.ssafy.fullerting.bidLog.model.entity.BidLog;
import com.ssafy.fullerting.bidLog.repository.BidRepository;
import com.ssafy.fullerting.bidLog.service.BidService;
import com.ssafy.fullerting.deal.exception.DealErrorCode;
import com.ssafy.fullerting.deal.exception.DealException;
import com.ssafy.fullerting.deal.model.dto.request.DealstartRequest;
import com.ssafy.fullerting.deal.model.dto.response.DealstartResponse;
import com.ssafy.fullerting.deal.model.entity.Deal;
import com.ssafy.fullerting.deal.repository.DealRepository;
import com.ssafy.fullerting.exArticle.exception.ExArticleErrorCode;
import com.ssafy.fullerting.exArticle.exception.ExArticleException;
import com.ssafy.fullerting.exArticle.model.entity.ExArticle;
import com.ssafy.fullerting.exArticle.repository.ExArticleRepository;
import com.ssafy.fullerting.exArticle.service.ExArticleService;
import com.ssafy.fullerting.global.config.BidNotification;
import com.ssafy.fullerting.user.model.entity.MemberProfile;
import com.ssafy.fullerting.user.repository.MemberRepository;
import com.ssafy.fullerting.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.PessimisticLockException;
import org.hibernate.exception.LockTimeoutException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailMessage;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j

public class BidConsumerService {

    private final EventAlarmService eventAlarmService;
    private final UserService userService;
    private final ExArticleService exArticleService;
    private final BidService bidService;
    private final MemberRepository memberRepository;
    private final BidProducerService bidProducerService;
    private final BidRepository bidRepository;

    private final ExArticleRepository exArticleRepository;
    private final DealRepository dealRepository;
    private final ObjectMapper objectMapper;
    private final SimpMessagingTemplate messagingTemplate;

    @KafkaListener(topics = "bid_requests", groupId = "bid-group", containerFactory = "bidKafkaListenerContainerFactory")
    @Transactional
    public void consumeBidRequest(String messageJson) {
        try {
            BidRequestMessage message = objectMapper.readValue(messageJson, BidRequestMessage.class);

            Long exArticleId = message.getExArticleId();
            int dealCurPrice = message.getDealCurPrice();
            String bidderUserName = message.getBidderUserName();

            log.info("입찰 요청 수신 - 게시글 ID: {}, 입찰가: {}, 사용자: {}", exArticleId, dealCurPrice, bidderUserName);

            // 게시글 조회 (낙관적 락 사용)
            ExArticle exArticle = exArticleRepository.findWithDealByIdwithLock(exArticleId)
                    .orElseThrow(() -> new ExArticleException(ExArticleErrorCode.NOT_EXISTS));

            Deal deal = exArticle.getDeal();
            if (deal == null) {
                throw new ExArticleException(ExArticleErrorCode.NOT_EXISTS);
            }

            // 현재 가격 확인
            int currentPrice = deal.getDealCurPrice();
            if (dealCurPrice <= currentPrice) {
                throw new RuntimeException("현재가보다 높은 금액을 입력해주세요. 현재가: " + currentPrice);
            }

            // 입찰자 정보 조회
            MemberProfile bidder = memberRepository.findByNickname(bidderUserName)
                    .orElseThrow(() -> new RuntimeException("회원 정보를 찾을 수 없습니다."));

            // 기존 입찰 내역 조회
            List<BidLog> existingBids = bidRepository.findByDealId(deal.getId());

            // 고유 입찰자 수 계산
            long uniqueBidderCount = existingBids.stream()
                    .map(BidLog::getUserId)
                    .distinct()
                    .count();

            // 현재 사용자의 기존 입찰 여부 확인
            boolean isNewBidder = existingBids.stream()
                    .noneMatch(bid -> bid.getUserId().equals(bidder.getId()));

            // 새로운 입찰자라면 카운트 증가
            if (isNewBidder) {
                uniqueBidderCount++;
            }

            // 입찰 내역 저장
            BidLog bidLog = BidLog.builder()
                    .deal(deal)
                    .userId(bidder.getId())
                    .bidLogPrice(dealCurPrice)
                    .localDateTime(LocalDateTime.now())
                    .build();

            bidRepository.save(bidLog);

            // 거래 정보 업데이트
            deal.setDealCurPrice(dealCurPrice);
            deal.setBidderCount((int) uniqueBidderCount); // 고유 입찰자 수 저장
            dealRepository.save(deal);

            // WebSocket으로 실시간 업데이트 전송
            Map<String, Object> wsMessage = new HashMap<>();
            wsMessage.put("type", "BID_UPDATE");
            wsMessage.put("bidLogId", bidLog.getId());
            wsMessage.put("exArticleId", exArticleId);
            wsMessage.put("dealCurPrice", dealCurPrice);
            wsMessage.put("bidderCount", uniqueBidderCount);
            wsMessage.put("userResponse", Map.of(
                    "nickname", bidder.getNickname(),
                    "thumbnail", bidder.getThumbnail()));
            wsMessage.put("localDateTime", LocalDateTime.now().toString());

            messagingTemplate.convertAndSend(
                    "/topic/bidding/" + exArticleId,
                    wsMessage);

            // 알림 전송
            bidProducerService.kafkaalarmproduce(bidder, exArticle, "/some/redirect/url");

        } catch (Exception e) {
            log.error("Kafka 입찰 메시지 처리 실패: {}", messageJson, e);
        }
    }

    @KafkaListener(topics = "kafka-alarm", groupId = "user-notifications", concurrency = "5", // 파티션 ≥ 5 필요
            // containerFactory = "kafkaJsonContainerFactory"
            containerFactory = "stringKafkaListenerContainerFactory")
    @Transactional
    public void kafkaalram(String string) {
        long receivedTime = System.currentTimeMillis();
        log.info("Received notification: {} at {}", string, receivedTime);

        // 수신 시간과 전송 시간을 비교하여 속도 측정
        try {
            // 수신한 BidNotification 객체를 처리하는 로직
            System.out.println("Received notification for member: " + string);
            BidNotification bidNotification = objectMapper.readValue(string, BidNotification.class);

            MemberProfile buyer = userService.getUserEntityById(bidNotification.getUserId());
            ExArticle article = exArticleService.getbyid(bidNotification.getArticleId());
            Deal deal;
            try {
                log.info("in try");
                deal = dealRepository.findByIdWithLock(article.getDeal().getId())
                        .orElseThrow(() -> new DealException(DealErrorCode.NOT_EXISTS));
            } catch (PessimisticLockException | LockTimeoutException e) {
                log.warn("Lock acquisition timeout for articleId: {}", bidNotification.getArticleId());
                messagingTemplate.convertAndSend("/sub/bidding/" + bidNotification.getArticleId(),
                        DealstartResponse.error("현재 입찰량이 많아 처리 중입니다. 잠시 후 다시 시도해주세요."));
                return;
            }

            // 게시물 정보
            // ExArticle exArticle =
            // exArticleRepository.findById(bidNotification.getArticleId()).orElseThrow(()
            // -> new ExArticleException(
            // ExArticleErrorCode.NOT_EXISTS));
            Long bidUserId = bidNotification.getUserId();

            MemberProfile bidUser = userService.getUserEntityById(bidUserId);
            DealstartRequest dealstartRequest = DealstartRequest.builder()
                    .dealCurPrice(bidNotification.getPrice())
                    .redirectURL(bidNotification.getRedirectUrl())
                    .exArticleId(bidNotification.getArticleId())
                    .build();

            // 입찰 기록(bid_log) 저장
            BidLog socketdealbid = bidService.socketdealbid(article,
                    BidProposeRequest.builder()
                            .dealCurPrice(dealstartRequest.getDealCurPrice())
                            .userId(bidNotification.getUserId())
                            .build());

            int bidderCount = bidService.getBidderCount(deal);

            int maxBidPrice = bidService.getMaxBidPrice(article);

            // WebSocket을 통해 메시지 전송
            messagingTemplate.convertAndSend("/sub/bidding/" + bidNotification.getArticleId(),
                    DealstartResponse.builder()
                            .bidLogId(socketdealbid.getId())
                            .exArticleId(bidNotification.getUserId())
                            .userResponse(bidUser.toResponse())
                            .dealCurPrice(dealstartRequest.getDealCurPrice())
                            .maxPrice(maxBidPrice)
                            .bidderCount(bidderCount)
                            .build());

            eventAlarmService.notifyChatRoomAuthor(buyer, article, bidNotification.getRedirectUrl());

            // 추가적인 처리 로직을 여기에 구현
        } catch (Exception e) {
            // 예외 타입에 따라 다른 처리를 할 수 있습니다.
            System.err.println(
                    "Error processing bid notification: " + e.getClass().getSimpleName() + " - " + e.getMessage());
            e.printStackTrace();

        }

    }

}
