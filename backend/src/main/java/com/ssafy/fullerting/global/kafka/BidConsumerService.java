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

                        log.info("?ûÖÏ∞? ?öîÏ≤? ?àò?ã† - Í≤åÏãúÍ∏? ID: {}, ?ûÖÏ∞∞Í??: {}, ?Ç¨?ö©?ûê: {}", exArticleId, dealCurPrice, bidderUserName);

                        ExArticle exArticle = exArticleRepository.findWithDealByIdwithLock(exArticleId)
                                        .orElseThrow(() -> new ExArticleException(ExArticleErrorCode.NOT_EXISTS));
                        Deal deal = exArticle.getDeal();
                        if (deal == null) {
                                throw new ExArticleException(ExArticleErrorCode.NOT_EXISTS);
                        }

                        int currentPrice = deal.getDealCurPrice();
                        if (dealCurPrice <= currentPrice) {
                                throw new RuntimeException(
                                                "?òÑ?û¨Í∞?Î≥¥Îã§ ?Üí??? Í∏àÏï°?ùÑ ?ûÖ?†•?ï¥Ï£ºÏÑ∏?öî. ?òÑ?û¨Í∞?: " + currentPrice);
                        }

                        MemberProfile bidder = memberRepository.findByNickname(bidderUserName)
                                        .orElseThrow(() -> new RuntimeException("?öå?õê ?†ïÎ≥¥Î?? Ï∞æÏùÑ ?àò ?óÜ?äµ?ãà?ã§."));

                        List<BidLog> existingBids = bidRepository.findByDealId(deal.getId().toString());
                        long uniqueBidderCount = existingBids.stream()
                                        .map(BidLog::getUserId)
                                        .distinct()
                                        .count();
                        boolean isNewBidder = existingBids.stream()
                                        .noneMatch(bid -> bid.getUserId().equals(bidder.getId()));
                        if (isNewBidder)
                                uniqueBidderCount++;

                        // ?ûÖÏ∞? ?Ç¥?ó≠ ????û• (?ò§Ïß? ?ó¨Í∏∞ÏÑúÎß?!)
                        BidLog bidLog = BidLog.builder()
                                        .deal(deal)
                                        .userId(bidder.getId())
                                        .bidLogPrice(dealCurPrice)
                                        .localDateTime(LocalDateTime.now())
                                        .build();

                        bidRepository.save(bidLog);
                        bidService.updateRedisCache(exArticle, bidLog, bidder.toResponse()); // ?†à?îî?ä§?óê ?ï¥?ãπ ?ûëÎ¨ºÏóê ????ïú ÏµúÍ≥† ?ûÖÏ∞? Í∏àÏï°
                                                                                             // ?óÖ?ç∞?ù¥?ä∏.

                        // Í±∞Îûò ?†ïÎ≥? ?óÖ?ç∞?ù¥?ä∏
                        deal.setDealCurPrice(dealCurPrice);
                        deal.setBidderCount((int) uniqueBidderCount);
                        dealRepository.save(deal);

                        // WebSocket?úºÎ°? ?ã§?ãúÍ∞? ?óÖ?ç∞?ù¥?ä∏ ?†Ñ?Ü°
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

                        // ?ïåÎ¶? ?†Ñ?Ü°
                        bidProducerService.kafkaalarmproduce(bidder, exArticle, "/some/redirect/url");

                } catch (Exception e) {
                        log.error("Kafka ?ûÖÏ∞? Î©îÏãúÏß? Ï≤òÎ¶¨ ?ã§?å®: {}", message, e);
                }
        }

        // [?àò?†ï]
        // ?èô?ùº?ïú ?Ü†?îΩ(bid_requests)Í≥? Í∑∏Î£π ID(bid-group)Î•? Í∞?Ïß? Î¶¨Ïä§?ÑàÍ∞? Ï§ëÎ≥µ?êò?ñ¥
        // ?ï†?îåÎ¶¨Ï???ù¥?Öò ?ãú?ûë ?ò§Î•òÍ?? Î∞úÏÉù?ï©?ãà?ã§.
        // ?ç∞?ù¥?Ñ∞ ?†ï?ï©?Ñ±?ùÑ Î≥¥Ïû•?ïò?äî ÎπÑÍ???†Å ?ùΩ(findWithDealByIdwithLock)?ùÑ ?Ç¨?ö©?ïò?äî ?úÑ?ùò
        // consumeBidRequest Î©îÏÑú?ìúÍ∞?
        // ?ö¥?òÅ Î°úÏßÅ?ù¥ÎØ?Î°?,
        // ?Öå?ä§?ä∏?ö©?úºÎ°? Ï∂îÏ†ï?êò?äî ?ù¥ Î¶¨Ïä§?Ñà?äî Ï£ºÏÑù Ï≤òÎ¶¨?ïò?ó¨ ÎπÑÌôú?Ñ±?ôî?ï©?ãà?ã§.
        // @KafkaListener(topics = "bid_requests", groupId = "bid-group",
        // containerFactory = "bidKafkaListenerContainerFactory")
        @Transactional
        public void consumeBidRequestwithouLock(BidRequestMessage message) {
                try {
                        Long exArticleId = message.getExArticleId();
                        int dealCurPrice = message.getDealCurPrice();
                        String bidderUserName = message.getBidderUserName();

                        log.info("?ûÖÏ∞? ?öîÏ≤? ?àò?ã† - Í≤åÏãúÍ∏? ID: {}, ?ûÖÏ∞∞Í??: {}, ?Ç¨?ö©?ûê: {}", exArticleId, dealCu
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
                                                "?òÑ?û¨Í∞?Î≥¥Îã§ ?Üí??? Í∏àÏï°?ùÑ ?ûÖ?†•?ï¥Ï£ºÏÑ∏?öî. ?òÑ?û¨Í∞?: " + currentPrice);
                        }

                        MemberProfile bidder = memberRepository.findByNickname(bidderUserName)
                                        .orElseThrow(() -> new RuntimeException(
                                                        "?öå?õê ?†ïÎ≥¥Î?? Ï∞æÏùÑ ?àò ?óÜ?äµ?ãà?ã§."));

                        List<BidLog> existingBids = bidRepository.findByDealId(deal.getId().toString());
                        long uniqueBidderCount = existingBids.stream()
                                        .map(BidLog::getUserId)
                                        .distinct()
                                        .count();
                        boolean isNewBidder = existingBids.stream()
                                        .noneMatch(bid -> bid.getUserId().equals(bidder.getId()));
                        if (isNewBidder)
                                uniqueBidderCount++;

                        // ?ûÖÏ∞? ?Ç¥?ó≠ ????û• (?ò§Ïß? ?ó¨Í∏∞ÏÑúÎß?!)
                        BidLog bidLog = BidLog.builder()
                                        .deal(deal)
                                        .userId(bidder.getId())
                                        .bidLogPrice(dealCurPrice)
                                        .localDateTime(LocalDateTime.now())
                                        .build();

                        bidRepository.save(bidLog);
                        bidService.updateRedisCache(exArticle, bidLog, bidder.toResponse()); // ?†à?îî?ä§?óê ?ï¥?ãπ ?ûëÎ¨ºÏóê
                                                                                             // 
                                                                                             // ????ïú ÏµúÍ≥† ?ûÖÏ∞? Í∏àÏï°
                                                                                             // ?óÖ?ç∞?ù¥?ä∏.

                        // Í±∞Îûò ?†ïÎ≥? ?óÖ?ç∞?ù¥?ä∏
                        deal.setDealCurPrice(dealCurPrice);
                        deal.setBidderCount((int) uniqueBidderCount);
                        dealRepository.save(deal);

                        // WebSocket?úºÎ°? ?ã§?ãúÍ∞? ?óÖ?ç∞?ù¥?ä∏ ?†Ñ?Ü°
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

                        // ?ïåÎ¶? ?†Ñ?Ü°
                        bidProducerService.kafkaalarmproduce(bidder, exArticle, "/some/redirect/url");

                } catch (Exception e) {
                        log.error("Kafka ?ûÖÏ∞? Î©îÏãúÏß? Ï≤òÎ¶¨ ?ã§?å®: {}", message, e);
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

                        // BidLog ????û•??? ?ïòÏß? ?ïä?äî?ã§! (Ï§ëÎ≥µ ????û• Î∞©Ï??)
                        // BidProposeRequest bidProposeRequest = BidProposeRequest.builder()
                        // .dealCurPrice(bidNotification.getPrice())
                        // .userId(bidNotification.getUserid())
                        // .build();
                        // bidService.socketdealbid(article, bidProposeRequest); // ?Ç≠?†ú ?òê?äî Ï£ºÏÑù
                        // òÎ¶¨

                        // ?ïåÎ¶?/?õπ?ÜåÏº? ?ì± Î∂?Í∞? Î°úÏßÅÎß? ?ã§?ñâ
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
