//package com.ssafy.fullerting.global.config;
//
//import com.ssafy.fullerting.alarm.service.EventAlarmService;
//import com.ssafy.fullerting.bidLog.model.dto.request.BidProposeRequest;
//import com.ssafy.fullerting.bidLog.model.entity.BidLog;
//import com.ssafy.fullerting.bidLog.service.BidService;
//import com.ssafy.fullerting.deal.model.dto.request.DealstartRequest;
//import com.ssafy.fullerting.deal.model.dto.response.DealstartResponse;
//import com.ssafy.fullerting.exArticle.exception.ExArticleErrorCode;
//import com.ssafy.fullerting.exArticle.exception.ExArticleException;
//import com.ssafy.fullerting.exArticle.model.entity.ExArticle;
//import com.ssafy.fullerting.exArticle.repository.ExArticleRepository;
//import com.ssafy.fullerting.security.model.entity.CustomAuthenticationToken;
//import com.ssafy.fullerting.user.model.entity.MemberProfile;
//import com.ssafy.fullerting.user.service.UserService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.messaging.handler.annotation.DestinationVariable;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Controller;
//
//@Controller
//@Slf4j
//@RequiredArgsConstructor
//public class MessageController {
////    private final SimpMessagingTemplate messagingTemplate;
//    private final ExArticleRepository exArticleRepository;
//    private final EventAlarmService eventAlarmService;
//    private final BidService bidService;
//    private final UserService userService;
//    private final BidProducerService bidProducerService; // Kafka Producer Service 추가
//
//    @MessageMapping("/bidding/{exArticleId}/messages")
//    public void bidBroker(@DestinationVariable Long exArticleId, SimpMessageHeaderAccessor headerAccessor, DealstartRequest dealstartRequest) {
//        CustomAuthenticationToken authentication = (CustomAuthenticationToken) headerAccessor.getSessionAttributes().get("userAuthentication");
//
//        if (authentication != null) {
//            Long bidUserId = authentication.getUserId();
//            MemberProfile bidUser = userService.getUserEntityById(bidUserId);
//
//            log.info("웹소켓에서 추출한 유저 : {}", bidUser.toString());
//
//            ExArticle exArticle = exArticleRepository.findById(exArticleId).orElseThrow(() -> new ExArticleException(ExArticleErrorCode.NOT_EXISTS));
//
//            int maxBidPrice = bidService.getMaxBidPrice(exArticle);
//            log.info("현재 최고가: {} ", maxBidPrice);
//            log.info("현재가 : {}", exArticle.getDeal().getDealCurPrice());
//            if (dealstartRequest.getDealCurPrice() <= maxBidPrice || dealstartRequest.getDealCurPrice() < exArticle.getDeal().getDealCurPrice()) {
//                throw new RuntimeException("최고가보다 낮거나 같은 입찰가 입력 : " + maxBidPrice);
//            }
//
//            BidLog socketdealbid = bidService.socketdealbid(exArticle, BidProposeRequest.builder()
//                    .dealCurPrice(dealstartRequest.getDealCurPrice())
//                    .userId(bidUserId)
//                    .build());
//
//            int bidderCount = bidService.getBidderCount(exArticle);
//            log.info("bidCount {}", bidderCount);
//
//            DealstartResponse dealstartResponse = DealstartResponse.builder()
//                    .bidLogId(socketdealbid.getId())
//                    .exArticleId(exArticleId)
//                    .userResponse(bidUser.toResponse())
//                    .dealCurPrice(dealstartRequest.getDealCurPrice())
//                    .maxPrice(maxBidPrice)
//                    .bidderCount(bidderCount)
//                    .build();
//
//            // Kafka에 입찰 메시지 전송
//            bidProducerService.sendBidMessage(exArticleId, dealstartResponse);
//
//            // WebSocket으로 메시지 전송
////            messagingTemplate.convertAndSend("/sub/bidding/" + exArticleId, dealstartResponse);
//
//            log.info("Message [{}] send by member: {} to bidding room: {}", dealstartRequest.getDealCurPrice(), exArticleId);
//            log.info("리디렉트 {} : ", dealstartRequest.getRedirectURL());
//            eventAlarmService.notifyAuctionBidReceived(bidUser, exArticle, dealstartRequest.getRedirectURL());
//
//        } else {
//            log.error("웹소켓 요청에 유저 정보없음");
//        }
//    }
//}
