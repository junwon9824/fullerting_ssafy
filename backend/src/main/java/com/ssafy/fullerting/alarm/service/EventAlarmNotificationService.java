package com.ssafy.fullerting.alarm.service;

import com.ssafy.fullerting.alarm.model.dto.request.AlarmPayload;
import lombok.extern.slf4j.Slf4j;
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
    private static final long RECONNECTION_TIMEOUT = 1000L;

    public SseEmitter subscribe(String userId) {
        SseEmitter emitter = new SseEmitter(TIMEOUT);
        emitterMap.put(userId, emitter);

        emitter.onCompletion(() -> emitterMap.remove(userId));
        emitter.onTimeout(() -> emitterMap.remove(userId));
        emitter.onError((e) -> emitterMap.remove(userId));

        log.info("SSE 구독왔나? : {}", emitter);
        log.info("SSE 구독왔나? : {}", emitterMap);
        return emitter;
    }

    public void sendToUser(AlarmPayload alarmPayload) {

        SseEmitter emitter = emitterMap.get(alarmPayload.getReceiveUserId().toString());
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().data(alarmPayload));
            } catch (IOException e) {
                emitter.completeWithError(e);
                emitterMap.remove(alarmPayload.getReceiveUserId().toString());
            }
        }
    }
}
