package com.ssafy.fullerting.global.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.fullerting.alarm.service.EventAlarmService;
import com.ssafy.fullerting.exArticle.model.entity.ExArticle;
import com.ssafy.fullerting.exArticle.service.ExArticleService;
import com.ssafy.fullerting.global.config.BidNotification;
import com.ssafy.fullerting.user.model.entity.MemberProfile;
import com.ssafy.fullerting.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BidConsumerService {

    private final EventAlarmService eventAlarmService;
    private final UserService userService;
    private final ExArticleService exArticleService;
    private final ObjectMapper objectMapper;


    @KafkaListener(topics = "kafka-alarm", groupId = "user-notifications", containerFactory = "kafkaJsonContainerFactory")
    public void kafkaalram(String string  ) {

        try {
            // 수신한 BidNotification 객체를 처리하는 로직
            System.out.println("Received notification for member: " + string);
            BidNotification bidNotification = objectMapper.readValue(string, BidNotification.class);

            MemberProfile buyer = userService.getUserEntityById(bidNotification.getUserId());
            ExArticle article =exArticleService.getbyid(bidNotification.getArticleId());


            eventAlarmService.notifyChatRoomAuthor(buyer,article , bidNotification.getRedirectUrl() );


            // 추가적인 처리 로직을 여기에 구현
        } catch (Exception e) {
            // 예외 타입에 따라 다른 처리를 할 수 있습니다.
            System.err.println("Error processing bid notification: " + e.getClass().getSimpleName() + " - " + e.getMessage());
            e.printStackTrace();
        }

    }

}
