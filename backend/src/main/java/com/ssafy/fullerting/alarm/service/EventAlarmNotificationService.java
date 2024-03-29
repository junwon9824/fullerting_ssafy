package com.ssafy.fullerting.alarm.service;

import com.ssafy.fullerting.alarm.model.entity.EventAlarm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventAlarmNotificationService {
    private final SimpMessagingTemplate simpMessagingTemplate;

    public void sendAlarmToReceiveUser(EventAlarm alarm) {
        String destination = "/queue/alarms";
        var message = Map.of(
                "alarmId", alarm.getAlarmId(),
                "content", alarm.getContent(),
                "type", alarm.getType().toString(),
                "redirectURL", alarm.getRedirect()
        );
        simpMessagingTemplate.convertAndSendToUser(alarm.getReceiveUser().getUsername(),
                destination,
                message);
    }
}
