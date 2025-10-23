package com.ssafy.fullerting.global.kafka;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.ssafy.fullerting.alarm.service.EventAlarmService;
import com.ssafy.fullerting.bidLog.model.entity.BidLog;
import com.ssafy.fullerting.bidLog.repository.BidRepository;
import com.ssafy.fullerting.bidLog.service.BidService;
import com.ssafy.fullerting.deal.exception.DealErrorCode;
import com.ssafy.fullerting.deal.exception.DealException;
import com.ssafy.fullerting.deal.model.dto.response.DealstartResponse;
import com.ssafy.fullerting.deal.model.entity.Deal;
import com.ssafy.fullerting.deal.repository.DealRepository;
import com.ssafy.fullerting.exArticle.exception.ExArticleErrorCode;
import com.ssafy.fullerting.exArticle.exception.ExArticleException;
import com.ssafy.fullerting.exArticle.model.entity.ExArticle;
import com.ssafy.fullerting.exArticle.repository.ExArticleRepository;
import com.ssafy.fullerting.global.config.BidNotification;
import com.ssafy.fullerting.user.model.entity.MemberProfile;
import com.ssafy.fullerting.user.repository.MemberRepository;
import com.ssafy.fullerting.user.service.UserService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BidConsumerService {

        private final EventAlarmService eventAlarmService;
        private final UserService userService;
        private final BidService bidService;
        private final MemberRepository memberRepository;
        private final BidProducerService bidProducerService;
        private final BidRepository bidRepository;
        private final ExArticleRepository exArticleRepository;
        private final DealRepository dealRepository;
        private final SimpMessagingTemplate messagingTemplate;

        @KafkaListener(topics = "bid_requests", groupId = "bid-group", containerFactory = "bidKafkaListenerContainerFactory")
        @Transactional
        public void consumeBidRequest(BidRequestMessage message) {
                try {
                        Long exArticleId = message.getExArticleId();
                        int dealCurPrice = message.getDealCurPrice();
                        String bidderUserName = message.getBidderUserName();

                        log.info("입찰 요청 수신 - 게시글 ID: {}, 입찰가: {}, 사용자: {}", exArticleId, dealCurPrice, bidderUserName);

                        ExArticle exArticle = exArticleRepository.findWithDealByIdwithLock(exArticleId)
                                        .orElseThrow(() -> new ExArticleException(ExArticleErrorCode.NOT_EXISTS));
                        Deal deal = exArticle.getDeal();
                        if (deal == null) {
                                throw new ExArticleException(ExArticleErrorCode.NOT_EXISTS);
                        }

                        int currentPrice = deal.getDealCurPrice();
                        if (dealCurPrice <= currentPrice) {
                                throw new RuntimeException(
                                                "현재가보다 높은 금액을 입력해주세요. 현재가: " + currentPrice);
                        }

                        MemberProfile bidder = memberRepository.findByNickname(bidderUserName)
                                        .orElseThrow(() -> new RuntimeException("회원 정보를 찾을 수 없습니다."));

                        List<BidLog> existingBids = bidRepository.findByDealId(deal.getId().toString());
                        long uniqueBidderCount = existingBids.stream()
                                        .map(BidLog::getUserId)
                                        .distinct()
                                        .count();
                        boolean isNewBidder = existingBids.stream()
                                        .noneMatch(bid -> bid.getUserId().equals(bidder.getId()));
                        if (isNewBidder)
                                uniqueBidderCount++;

                        // �엯李� �궡�뿭 ����옣 (�삤吏� �뿬湲곗꽌留�!)
                        // 입찰 내역 저장 (오직 여기서만!)
                        BidLog bidLog = BidLog.builder()
                                        .deal(deal)
                                        .userId(bidder.getId())
                                        .bidLogPrice(dealCurPrice)
                                        .localDateTime(LocalDateTime.now())
                                        .build();

                        bidRepository.save(bidLog);
                        bidService.updateRedisCache(exArticle, bidLog, bidder.toResponse()); // �젅�뵒�뒪�뿉 �빐�떦 �옉臾쇱뿉
                                                                                             // ����븳 理쒓퀬 �엯李� 湲덉븸
                                                                                             // �뾽�뜲�씠�듃.
                        bidService.updateRedisCache(exArticle, bidLog, bidder.toResponse()); // 레디스에 해당 작물에 대한 최고 입찰 금액
                                                                                             // 업데이트.

                        // 嫄곕옒 �젙蹂� �뾽�뜲�씠�듃
                        // 거래 정보 업데이트
                        deal.setDealCurPrice(dealCurPrice);
                        deal.setBidderCount((int) uniqueBidderCount);
                        dealRepository.save(deal);

                        // WebSocket�쑝濡� �떎�떆媛� �뾽�뜲�씠�듃 �쟾�넚
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

                        // �븣由� �쟾�넚
                        // 알림 전송
                        bidProducerService.kafkaalarmproduce(bidder, exArticle, "/some/redirect/url");

                } catch (Exception e) {
                        log.error("Kafka 입찰 메시지 처리 실패: {}", message, e);
                }
        }

        // [�닔�젙]
        // �룞�씪�븳 �넗�뵿(bid_requests)怨� 洹몃９ ID(bid-group)瑜� 媛�吏� 由ъ뒪�꼫媛� 以묐났�릺�뼱
        // �븷�뵆由ъ���씠�뀡 �떆�옉 �삤瑜섍�� 諛쒖깮�빀�땲�떎.
        // �뜲�씠�꽣 �젙�빀�꽦�쓣 蹂댁옣�븯�뒗 鍮꾧���쟻 �씫(findWithDealByIdwithLock)�쓣 �궗�슜�븯�뒗 �쐞�쓽
        // consumeBidRequest 硫붿꽌�뱶媛�
        // �슫�쁺 濡쒖쭅�씠誘�濡�,
        // �뀒�뒪�듃�슜�쑝濡� 異붿젙�릺�뒗 �씠 由ъ뒪�꼫�뒗 二쇱꽍 泥섎━�븯�뿬 鍮꾪솢�꽦�솕�빀�땲�떎.
        // @KafkaListener(topics = "bid_requests", groupId = "bid-group",
        // containerFactory = "bidKafkaListenerContainerFactory")
        /**
         * [수정 제안]
         * 동일한 토픽(bid_requests)과 그룹 ID(bid-group)를 가진 리스너가 중복되면 애플리케이션 시작 시 오류가 발생할 수 있습니다.
         * 데이터 정합성을 보장하는 비관적 락(findWithDealByIdwithLock)을 사용하는 위의 consumeBidRequest 메서드가 실제 운영 로직이므로,
         * 락이 없는 상황을 테스트하기 위한 이 메서드는 주석 처리하여 비활성화하는 것을 권장합니다.
         */
        // @KafkaListener(topics = "bid_requests", groupId = "bid-group", containerFactory = "bidKafkaListenerContainerFactory")
        @Transactional
        public void consumeBidRequestwithouLock(BidRequestMessage message) {
                try {
                        Long exArticleId = message.getExArticleId();
                        int dealCurPrice = message.getDealCurPrice();
                        String bidderUserName = message.getBidderUserName();

                        log.info("입찰 요청 수신 - 게시글 ID: {}, 입찰가: {}, 사용자: {}", exArticleId, dealCurPrice,
                                        bidderUserName);

                        ExArticle exArticle = exArticleRepository.findWithDealById(exArticleId)
                                        .orElseThrow(() -> new ExArticleException(ExArticleErrorCode.NOT_EXISTS));
                        Deal deal = exArticle.getDeal();
                        if (deal == null) {
                                throw new ExArticleException(ExArticleErrorCode.NOT_EXISTS);
                        }

                        int currentPrice = deal.getDealCurPrice();
                        if (dealCurPrice <= currentPrice) {
                                throw new RuntimeException(
                                                "현재가보다 높은 금액을 입력해주세요. 현재가: " + currentPrice);
                        }

                        MemberProfile bidder = memberRepository.findByNickname(bidderUserName)
                                        .orElseThrow(() -> new RuntimeException("�쉶�썝 �젙蹂대�� 李얠쓣 �닔 �뾾�뒿�땲�떎."));
                                        .orElseThrow(() -> new RuntimeException("회원 정보를 찾을 수 없습니다."));

                        List<BidLog> existingBids = bidRepository.findByDealId(deal.getId().toString());
                        long uniqueBidderCount = existingBids.stream()
                                        .map(BidLog::getUserId)
                                        .distinct()
                                        .count();
                        boolean isNewBidder = existingBids.stream()
                                        .noneMatch(bid -> bid.getUserId().equals(bidder.getId()));
                        if (isNewBidder)
                                uniqueBidderCount++;

                        // �엯李� �궡�뿭 ����옣 (�삤吏� �뿬湲곗꽌留�!)
                        // 입찰 내역 저장 (오직 여기서만!)
                        BidLog bidLog = BidLog.builder()
                                        .deal(deal)
                                        .userId(bidder.getId())
                                        .bidLogPrice(dealCurPrice)
                                        .localDateTime(LocalDateTime.now())
                                        .build();

                        bidRepository.save(bidLog);
                        bidService.updateRedisCache(exArticle, bidLog, bidder.toResponse()); // �젅�뵒�뒪�뿉 �빐�떦 �옉臾쇱뿉
                                                                                             // ����븳 理쒓퀬 �엯李� 湲덉븸
                                                                                             // �뾽�뜲�씠�듃.
                        bidService.updateRedisCache(exArticle, bidLog, bidder.toResponse()); // 레디스에 해당 작물에
                                                                                             // 대한 최고 입찰 금액 업데이트.

                        // 嫄곕옒 �젙蹂� �뾽�뜲�씠�듃
                        // 거래 정보 업데이트
                        deal.setDealCurPrice(dealCurPrice);
                        deal.setBidderCount((int) uniqueBidderCount);
                        dealRepository.save(deal);

                        // WebSocket�쑝濡� �떎�떆媛� �뾽�뜲�씠�듃 �쟾�넚
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

                        // �븣由� �쟾�넚
                        // 알림 전송
                        bidProducerService.kafkaalarmproduce(bidder, exArticle, "/some/redirect/url");

                } catch (Exception e) {
                        log.error("Kafka 입찰 메시지 처리 실패: {}", message, e);
                }
        }

        @KafkaListener(topics = "kafka-alarm", groupId = "user-notifications", concurrency = "5", containerFactory = "bidNotificationKafkaListenerContainerFactory")
        @Transactional
        public void kafkaalram(BidNotification bidNotification) {
                try {
                        log.info("Kafka message: {}", bidNotification);

                        ExArticle article = exArticleRepository.findById(bidNotification.getArticleid())
                                        .orElseThrow(() -> new ExArticleException(ExArticleErrorCode.NOT_EXISTS));
                        Deal deal = article.getDeal();
                        if (deal == null) {
                                throw new DealException(DealErrorCode.NOT_EXISTS);
                        }

                        // BidLog ����옣��� �븯吏� �븡�뒗�떎! (以묐났 ����옣 諛⑹��)
                        // BidLog 저장은 하지 않는다! (중복 저장 방지)
                        // BidProposeRequest bidProposeRequest = BidProposeRequest.builder()
                        // .dealCurPrice(bidNotification.getPrice())
                        // .userId(bidNotification.getUserid())
                        // .build();
                        // bidService.socketdealbid(article, bidProposeRequest); // �궘�젣 �삉�뒗 二쇱꽍泥섎━
                        // bidService.socketdealbid(article, bidProposeRequest); // 삭제 또는 주석처리

                        // �븣由�/�쎒�냼耳� �벑 遺�媛� 濡쒖쭅留� �떎�뻾
                        // 알림/웹소켓 등 부가 로직만 실행
                        MemberProfile bidUser = userService.getUserEntityById(bidNotification.getUserid());
                        int bidderCount = bidService.getBidderCount(deal);
                        int maxBidPrice = bidService.getMaxBidPrice(article);

                        messagingTemplate.convertAndSend("/sub/bidding/" + bidNotification.getArticleid(),
                                        DealstartResponse.builder()
                                                        .bidLogId(null)
                                                        .exArticleId(bidNotification.getArticleid())
                                                        .userResponse(bidUser.toResponse())
                                                        .dealCurPrice(bidNotification.getPrice())
                                                        .maxPrice(maxBidPrice)
                                                        .bidderCount(bidderCount)
                                                        .build());

                        eventAlarmService.notifyChatRoomAuthor(bidUser, article, bidNotification.getRedirectUrl());

                } catch (Exception e) {
                        log.error("Error processing bid notification: {}", e.getMessage(), e);
                }
        }
}
