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

                        log.info("?���? ?���? ?��?�� - 게시�? ID: {}, ?��찰�??: {}, ?��?��?��: {}", exArticleId, dealCurPrice, bidderUserName);

                        ExArticle exArticle = exArticleRepository.findWithDealByIdwithLock(exArticleId)
                                        .orElseThrow(() -> new ExArticleException(ExArticleErrorCode.NOT_EXISTS));
                        Deal deal = exArticle.getDeal();
                        if (deal == null) {
                                throw new ExArticleException(ExArticleErrorCode.NOT_EXISTS);
                        }

                        int currentPrice = deal.getDealCurPrice();
                        if (dealCurPrice <= currentPrice) {
                                throw new RuntimeException(
                                                "?��?���?보다 ?��??? 금액?�� ?��?��?��주세?��. ?��?���?: " + currentPrice);
                        }

                        MemberProfile bidder = memberRepository.findByNickname(bidderUserName)
                                        .orElseThrow(() -> new RuntimeException("?��?�� ?��보�?? 찾을 ?�� ?��?��?��?��."));

                        List<BidLog> existingBids = bidRepository.findByDealId(deal.getId().toString());
                        long uniqueBidderCount = existingBids.stream()
                                        .map(BidLog::getUserId)
                                        .distinct()
                                        .count();
                        boolean isNewBidder = existingBids.stream()
                                        .noneMatch(bid -> bid.getUserId().equals(bidder.getId()));
                        if (isNewBidder)
                                uniqueBidderCount++;

                        // ?���? ?��?�� ????�� (?���? ?��기서�?!)
                        BidLog bidLog = BidLog.builder()
                                        .deal(deal)
                                        .userId(bidder.getId())
                                        .bidLogPrice(dealCurPrice)
                                        .localDateTime(LocalDateTime.now())
                                        .build();

                        bidRepository.save(bidLog);
                        bidService.updateRedisCache(exArticle, bidLog, bidder.toResponse()); // ?��?��?��?�� ?��?�� ?��물에 ????�� 최고 ?���? 금액
                                                                                             // ?��?��?��?��.

                        // 거래 ?���? ?��?��?��?��
                        deal.setDealCurPrice(dealCurPrice);
                        deal.setBidderCount((int) uniqueBidderCount);
                        dealRepository.save(deal);

                        // WebSocket?���? ?��?���? ?��?��?��?�� ?��?��
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

                        // ?���? ?��?��
                        bidProducerService.kafkaalarmproduce(bidder, exArticle, "/some/redirect/url");

                } catch (Exception e) {
                        log.error("Kafka ?���? 메시�? 처리 ?��?��: {}", message, e);
                }
        }

        // [?��?��]
        // ?��?��?�� ?��?��(bid_requests)�? 그룹 ID(bid-group)�? �?�? 리스?���? 중복?��?��
        // ?��?��리�???��?�� ?��?�� ?��류�?? 발생?��?��?��.
        // ?��?��?�� ?��?��?��?�� 보장?��?�� 비�???�� ?��(findWithDealByIdwithLock)?�� ?��?��?��?�� ?��?��
        // consumeBidRequest 메서?���?
        // ?��?�� 로직?���?�?,
        // ?��?��?��?��?���? 추정?��?�� ?�� 리스?��?�� 주석 처리?��?�� 비활?��?��?��?��?��.
        // @KafkaListener(topics = "bid_requests", groupId = "bid-group",
        // containerFactory = "bidKafkaListenerContainerFactory")
        @Transactional
        public void consumeBidRequestwithouLock(BidRequestMessage message) {
                try {
                        Long exArticleId = message.getExArticleId();
                        int dealCurPrice = message.getDealCurPrice();
                        String bidderUserName = message.getBidderUserName();

                        log.info("?���? ?���? ?��?�� - 게시�? ID: {}, ?��찰�??: {}, ?��?��?��: {}", exArticleId, dealCu
                                        Price,
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
                                                "?��?���?보다 ?��??? 금액?�� ?��?��?��주세?��. ?��?���?: " + currentPrice);
                        }

                        MemberProfile bidder = memberRepository.findByNickname(bidderUserName)
                                        .orElseThrow(() -> new RuntimeException(
                                                        "?��?�� ?��보�?? 찾을 ?�� ?��?��?��?��."));

                        List<BidLog> existingBids = bidRepository.findByDealId(deal.getId().toString());
                        long uniqueBidderCount = existingBids.stream()
                                        .map(BidLog::getUserId)
                                        .distinct()
                                        .count();
                        boolean isNewBidder = existingBids.stream()
                                        .noneMatch(bid -> bid.getUserId().equals(bidder.getId()));
                        if (isNewBidder)
                                uniqueBidderCount++;

                        // ?���? ?��?�� ????�� (?���? ?��기서�?!)
                        BidLog bidLog = BidLog.builder()
                                        .deal(deal)
                                        .userId(bidder.getId())
                                        .bidLogPrice(dealCurPrice)
                                        .localDateTime(LocalDateTime.now())
                                        .build();

                        bidRepository.save(bidLog);
                        bidService.updateRedisCache(exArticle, bidLog, bidder.toResponse()); // ?��?��?��?�� ?��?�� ?��물에
                                                                                             // 
                                                                                             // ????�� 최고 ?���? 금액
                                                                                             // ?��?��?��?��.

                        // 거래 ?���? ?��?��?��?��
                        deal.setDealCurPrice(dealCurPrice);
                        deal.setBidderCount((int) uniqueBidderCount);
                        dealRepository.save(deal);

                        // WebSocket?���? ?��?���? ?��?��?��?�� ?��?��
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

                        // ?���? ?��?��
                        bidProducerService.kafkaalarmproduce(bidder, exArticle, "/some/redirect/url");

                } catch (Exception e) {
                        log.error("Kafka ?���? 메시�? 처리 ?��?��: {}", message, e);
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

                        // BidLog ????��??? ?���? ?��?��?��! (중복 ????�� 방�??)
                        // BidProposeRequest bidProposeRequest = BidProposeRequest.builder()
                        // .dealCurPrice(bidNotification.getPrice())
                        // .userId(bidNotification.getUserid())
                        // .build();
                        // bidService.socketdealbid(article, bidProposeRequest); // ?��?�� ?��?�� 주석
                        // �리

                        // ?���?/?��?���? ?�� �?�? 로직�? ?��?��
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
