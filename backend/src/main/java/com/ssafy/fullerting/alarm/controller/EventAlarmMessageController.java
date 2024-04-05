//package com.ssafy.fullerting.alarm.controller;
//
//import com.ssafy.fullerting.alarm.model.entity.EventAlarm;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Controller;
//
//import java.util.Map;
//
//@Slf4j
//@Controller
//@RequiredArgsConstructor
//public class EventAlarmMessageController {
//    private final SimpMessagingTemplate messagingTemplate;
//
//    // 알림함에 알림이 저장되면 타겟 사용자에게 푸쉬를 보낸다
//    public void sendAlarmToReceiveUser(EventAlarm alarm) {
//        // 사용자별 알림을 위한 대상 경로 설정
//        // Spring Security와 통합된 경우, '/user/queue/alarms'가 특정 사용자에게 메시지를 전송하는 데 사용될 수 있음
//        String destination = "/queue/alarms"; // 사용자별 구독 경로
//
//        // 알림 정보를 담은 메시지 객체 생성
//        var message = Map.of(
//                "alarmId", alarm.getAlarmId(),
//                "content", alarm.getContent(),
//                "type", alarm.getType().toString(),
//                "redirectURL", alarm.getRedirect()
//        );
//
//        // 실시간으로 알림을 대상 사용자에게 전송
//        messagingTemplate.convertAndSendToUser(alarm.getReceiveUser().getUsername(), destination, message);
//    }
//}
