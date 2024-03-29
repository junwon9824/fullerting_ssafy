package com.ssafy.fullerting.alarm.service;

import com.ssafy.fullerting.alarm.model.dto.request.AlarmPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class EventAlarmNotificationService {
    // thread-safe 한 컬렉션 객체로 sse emitter 객체를 관리
    private final Map<String, SseEmitter> emitterMap = new ConcurrentHashMap<>();
    private static final long TIMEOUT = 60 * 1000;
    private static final long HEARTBEAT_INTERVAL = 40 * 1000L; // 40초마다 하트비트 전송

    public SseEmitter subscribe(String userId) {
        SseEmitter emitter = new SseEmitter(TIMEOUT);
        emitterMap.put(userId, emitter);

        emitter.onCompletion(() -> emitterMap.remove(userId));
        emitter.onTimeout(() -> emitterMap.remove(userId));
        emitter.onError((e) -> emitterMap.remove(userId));

        log.info("SSE 구독요청 : {}", emitterMap.toString());
        return emitter;
    }

    public void sendToUser(AlarmPayload alarmPayload) {

        SseEmitter emitter = emitterMap.get(alarmPayload.getReceiveUserId().toString());
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event()
                        .data(alarmPayload));
            } catch (IOException e) {
                emitter.completeWithError(e);
                emitterMap.remove(alarmPayload.getReceiveUserId().toString());
            }
        }
    }

    // 하트비트 전송을 위한 메소드 추가
    @Scheduled(fixedRate = HEARTBEAT_INTERVAL)
    public void sendHeartbeat() {
        emitterMap.forEach((userId, emitter) -> {
            try {
                emitter.send(SseEmitter.event().data("heartbeat"));
                log.info("Heartbeat sent to {}", userId);
            } catch (IOException e) {
                emitter.completeWithError(e);
                emitterMap.remove(userId);
            }
        });
    }
}
