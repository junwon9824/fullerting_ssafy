package com.ssafy.fullerting.global.kafka;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.fullerting.alarm.service.EventAlarmService;
import com.ssafy.fullerting.bidLog.service.BidService;
import com.ssafy.fullerting.deal.model.dto.request.DealstartRequest;
import com.ssafy.fullerting.exArticle.repository.ExArticleRepository;
import com.ssafy.fullerting.security.model.entity.CustomAuthenticationToken;
import com.ssafy.fullerting.user.model.entity.MemberProfile;
import com.ssafy.fullerting.user.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class KafkaMessageController {
    private final SimpMessagingTemplate messagingTemplate;
    private final ExArticleRepository exArticleRepository;
    private final EventAlarmService eventAlarmService;
    private final BidService bidService;

    private final UserService userService;
    private final BidProducerService bidProducerService;

    // 이 메서드를 호출하는건 pub 로 시작하는 경로..
    @MessageMapping("/bidding/{exArticleId}/messages")
    public void bidBroker(@DestinationVariable("exArticleId") Long exArticleId,
            SimpMessageHeaderAccessor headerAccessor, DealstartRequest dealstartRequest) {

        // 트랜잭션 상태 확인
        boolean transactionActive = TransactionSynchronizationManager.isActualTransactionActive();
        log.info("트랜잭션 상태 확인: {}", transactionActive ? "활성화됨" : "비활성화됨");

        if (!transactionActive) {
            log.warn("트랜잭션이 활성화되지 않았습니다. 트랜잭션이 필요한 작업입니다.");
        }

        // 세션 속성에서 CustomAuthenticationToken 조회
        CustomAuthenticationToken authentication = (CustomAuthenticationToken) headerAccessor.getSessionAttributes()
                .get("userAuthentication");

        if (authentication != null) {
            Long bidUserId = authentication.getUserId();
            MemberProfile bidUser = userService.getUserEntityById(bidUserId);
            log.info("웹소켓에서 추출한 유저 : {}", bidUser.toString());

            log.info("in MEssagemapping");
            log.info("Message [{}] sent by member: {} to bidding room: {}", dealstartRequest.getDealCurPrice(),
                    exArticleId);
            log.info("리디렉트 URL: {}", dealstartRequest.getRedirectURL());

            try {
                bidProducerService.sendBidRequest(exArticleId,
                        dealstartRequest.getDealCurPrice(),
                        bidUser);
            } catch (RuntimeException e) {
                log.warn("❌ 입찰 실패: {}", e.getMessage());
                // 필요시 WebSocket 응답으로 클라이언트에 실패 메시지 전송 가능
            }

        } else {
            log.error("웹소켓 요청에 유저 정보없음");
        }
    }

}
