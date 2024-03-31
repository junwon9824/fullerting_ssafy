package com.ssafy.fullerting.alarm.service;

import com.ssafy.fullerting.alarm.model.dto.request.AlarmPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
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
    private static final long TIMEOUT = 45 * 1000;
    private static final long HEARTBEAT_INTERVAL = 25 * 1000L; // 25초마다 하트비트 전송

    public SseEmitter subscribe(String userId) {
        log.info("SSE 구독 요청 시작: {} (스레드: {})", userId, Thread.currentThread().getName());
        SseEmitter emitter = new SseEmitter(TIMEOUT);
        emitterMap.put(userId, emitter);

        emitter.onCompletion(() -> emitterMap.remove(userId));
        emitter.onTimeout(() -> emitterMap.remove(userId));
        emitter.onError((e) -> emitterMap.remove(userId));

        log.info("SSE 구독 요청 완료: {} (스레드: {})", userId, Thread.currentThread().getName());
        return emitter;
    }

    @Async("notiExecutor")
    public void sendAsync(AlarmPayload alarmPayload) {
        log.info("비동기 메서드 시작: {} (스레드: {})", alarmPayload, Thread.currentThread().getName());
        SseEmitter emitter = emitterMap.get(alarmPayload.getReceiveUserId().toString());
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().data(alarmPayload));
                log.info("서버로부터 SSE 도착 (비동기) : {} ", alarmPayload.toString());
            } catch (IOException e) {
                log.error("SSE 전송 실패 : {}", e.getMessage());
                emitter.completeWithError(e);
                emitterMap.remove(alarmPayload.getReceiveUserId().toString());
            }
        }
        log.info("비동기 메서드 종료: {} (스레드: {})", alarmPayload, Thread.currentThread().getName());
    }




//    public void sendToUser(AlarmPayload alarmPayload) {
//
//        SseEmitter emitter = emitterMap.get(alarmPayload.getReceiveUserId().toString());
//        if (emitter != null) {
//            try {
//                emitter.send(SseEmitter.event()
//                        .data(alarmPayload));
//                log.info("서버로부터 SSE 도착 : {} ", alarmPayload.toString());
//            } catch (IOException e) {
//                emitter.completeWithError(e);
//                emitterMap.remove(alarmPayload.getReceiveUserId().toString());
//            }
//        }
//    }

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
