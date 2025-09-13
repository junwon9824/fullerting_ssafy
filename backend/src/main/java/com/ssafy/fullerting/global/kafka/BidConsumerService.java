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

                        log.info("�엯李� �슂泥� �닔�떊 - 寃뚯떆湲� ID: {}, �엯李곌��: {}, �궗�슜�옄: {}", exArticleId, dealCu
                                        Price, bidderUserName);

                        ExArticle exArticle = exArticleRepository.findWithDealByIdwithLock(exArticleId)
                                        .orElseThrow(() -> new ExArticleException(ExArticleErrorCode.NOT_EXISTS));
                        Deal deal = exArticle.getDeal();
                        if (deal == null) {
                                throw new ExArticleException(ExArticleErrorCode.NOT_EXISTS);
                        }

                        int currentPrice = deal.getDealCurPrice();
                        if (dealCurPrice <= currentPrice) {
                                throw new RuntimeException(
                                                "�쁽�옱媛�蹂대떎 �넂��� 湲덉븸�쓣 �엯�젰�빐二쇱꽭�슂. �쁽�옱媛�: " + currentPrice);
                        }

                        MemberProfile bidder = memberRepository.findByNickname(bidderUserName)
                                        .orElseThrow(() -> new RuntimeException("�쉶�썝 �젙蹂대�� 李얠쓣 �닔 �뾾�뒿�땲�떎."));

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
                        BidLog bidLog = BidLog.builder()
                                        .deal(deal)
                                        .userId(bidder.getId())
                                        .bidLogPrice(dealCurPrice)
                                        .localDateTime(LocalDateTime.now())
                                        .build();

                        bidRepository.save(bidLog);
                        bidService.updateRedisCache(exArticle, bidLog, bidder.toResponse()); // �젅�뵒�뒪�뿉 �빐�떦 �옉臾쇱뿉
                                                                                             // ����븳 理쒓퀬 �엯李� 湲덉븸 �뾽�뜲�
                                                                                             // �듃.

                        // 嫄곕옒 �젙蹂� �뾽�뜲�씠�듃
                        deal.setDealCurPrice(dealCurPrice);
                        deal.setBidderCount((int) uniqueBidderCount);
                        dealRepository.save(deal);

                        // WebSocket�쑝濡� �떎�떆媛� �뾽�뜲�씠�듃 �쟾�넚
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
                        bidProducerService.kafkaalarmproduce(bidder, exArticle, "/some/redirect/url");

                } catch (Exception e) {
                        log.error("Kafka �엯李� 硫붿떆吏� 泥섎━ �떎�뙣: {}", message, e);
                }
        }

        // [수정]
        // 동일한 토픽(bid_requests)과 그룹 ID(bid-group)를 가진 리스너가 중복되어 애플리케이션 시작 오류가 발생합니다.
        // 데이터 정합성을 보장하는 비관적 락(findWithDealByIdwithLock)을 사용하는 위의 consumeBidRequest 메서드가
        // 운영 로직이므로,
        // 테스트용으로 추정되는 이 리스너는 주석 처리하여 비활성화합니다.
        // @KafkaListener(topics = "bid_requests", groupId = "bid-group",
        // containerFactory = "bidKafkaListenerContainerFactory")
        @Transactional
        public void consumeBidRequestwithouLock(BidRequestMessage message) {
                try {
                        Long exArticleId = message.getExArticleId();
                        int dealCurPrice = message.getDealCurPrice();
                        String bidderUserName = message.getBidderUserName();

                        log.info("�엯李� �슂泥� �닔�떊 - 寃뚯떆湲� ID: {}, �엯李곌��: {}, �궗�슜�옄: {}", exArticleId, dealCurPrice,
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
                                                "�쁽�옱媛�蹂대떎 �넂��� 湲덉븸�쓣 �엯�젰�빐二쇱꽭�슂. �쁽�옱媛�: " + currentPrice);
                        }

                        MemberProfile bidder = memberRepository.findByNickname(bidderUserName)
                                        .orElseThrow(() -> new RuntimeException("�쉶�썝 �젙蹂대�� 李얠쓣 �닔 �뾾�뒿�땲�떎."));

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

                        // 嫄곕옒 �젙蹂� �뾽�뜲�씠�듃
                        deal.setDealCurPrice(dealCurPrice);
                        deal.setBidderCount((int) uniqueBidderCount);
                        dealRepository.save(deal);

                        // WebSocket�쑝濡� �떎�떆媛� �뾽�뜲�씠�듃 �쟾�넚
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
                        bidProducerService.kafkaalarmproduce(bidder, exArticle, "/some/redirect/url");

                } catch (Exception e) {
                        log.error("Kafka �엯李� 硫붿떆吏� 泥섎━ �떎�뙣: {}", message, e);
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
                        // BidProposeRequest bidProposeRequest = BidProposeRequest.builder()
                        // .dealCurPrice(bidNotification.getPrice())
                        // .userId(bidNotification.getUserid())
                        // .build();
                        // bidService.socketdealbid(article, bidProposeRequest); // �궘�젣 �삉�뒗 二쇱꽍泥섎━

                        // �븣由�/�쎒�냼耳� �벑 遺�媛� 濡쒖쭅留� �떎�뻾
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
