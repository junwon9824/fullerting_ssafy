package com.ssafy.fullerting.alarm.service;

import com.ssafy.fullerting.alarm.model.dto.request.AlarmPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class EventAlarmNotificationService {
    // thread-safe 한 컬렉션 객체로 sse emitter 객체를 관리
    private final Map<String, SseEmitter> emitterMap = new ConcurrentHashMap<>();
    private final Set<String> completedEmitters = ConcurrentHashMap.newKeySet();
    private static final long TIMEOUT = 10 * 60 * 1000;
    private static final long HEARTBEAT_INTERVAL = 10 * 10000L; // 30초마다 하트비트 전송

    public SseEmitter subscribe(Long userId) {
        SseEmitter emitter = new SseEmitter(TIMEOUT);
        String userIdStr = userId.toString();
        emitterMap.put(userIdStr, emitter);

        emitter.onCompletion(() -> {
            emitterMap.remove(userIdStr);
            completedEmitters.remove(userIdStr);
        });

        emitter.onTimeout(() -> {
            emitter.complete();
            emitterMap.remove(userIdStr);
            completedEmitters.remove(userIdStr);
        });

        emitter.onError(e -> {
            log.error("SSE error for user {}: {}", userId, e.getMessage());
            emitterMap.remove(userIdStr);
            completedEmitters.remove(userIdStr);
        });

        log.info("SSE 구독 요청 완료: {} (스레드: {})", userId, Thread.currentThread().getName());
        return emitter;
    }

    @Async("notiExecutor")
    public void sendAsync(AlarmPayload alarmPayload) {
        String userId = alarmPayload.getReceiveUserId().toString();
        SseEmitter emitter = emitterMap.get(userId);

        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().data(alarmPayload));
                log.info("서버로부터 SSE 전송 성공: 사용자 {}, 데이터 {}", userId, alarmPayload);
            } catch (IOException e) {
                log.error("SSE 전송 실패: 사용자 {}, 오류 메시지 {}", userId, e.getMessage(), e);
                emitter.completeWithError(e);
                emitterMap.remove(userId);
                completedEmitters.remove(userId);
            }
        } else {
            log.warn("SSE 전송 시도 실패: 사용자 {}에 대한 Emitter 없음", userId);
        }
    }

    // 하트비트 전송을 위한 메소드 추가
    @Scheduled(fixedRate = HEARTBEAT_INTERVAL)
    public void sendHeartbeat() {
        if (emitterMap.isEmpty()) {
            return;
        }

        // Create a copy of the entry set to avoid ConcurrentModificationException
        Set<Map.Entry<String, SseEmitter>> entries = new java.util.HashSet<>(emitterMap.entrySet());

        for (Map.Entry<String, SseEmitter> entry : entries) {
            String userId = entry.getKey();
            SseEmitter emitter = entry.getValue();

            if (emitter == null || completedEmitters.contains(userId)) {
                emitterMap.remove(userId);
                completedEmitters.remove(userId);
                continue;
            }

            try {
                // Send heartbeat with timeout
                emitter.send(SseEmitter.event()
                        .data("heartbeat")
                        .reconnectTime(HEARTBEAT_INTERVAL));

            } catch (Exception e) {
                log.warn("Failed to send heartbeat to user {}: {}", userId, e.getMessage());
                try {
                    if (!completedEmitters.contains(userId)) {
                        emitter.complete();
                        completedEmitters.add(userId);
                    }
                } catch (Exception ex) {
                    log.debug("Error completing emitter for user {}: {}", userId, ex.getMessage());
                } finally {
                    emitterMap.remove(userId);
                    completedEmitters.remove(userId);
                }
            }
        }
    }
}
