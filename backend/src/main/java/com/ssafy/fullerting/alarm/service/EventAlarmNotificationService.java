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
    private static final long TIMEOUT = 10*60*1000;
    private static final long HEARTBEAT_INTERVAL = 10 * 1000L; // 30초마다 하트비트 전송

    public SseEmitter subscribe(Long userId) {
        log.info("SSE 구독 요청 시작: {} (스레드: {})", userId, Thread.currentThread().getName());
        SseEmitter emitter = new SseEmitter(TIMEOUT);
        emitterMap.put(userId.toString(), emitter);


        // FOR 503 ERR
        sendAsync(AlarmPayload.builder()
                .receiveUserId(userId)
                .alarmType("WELCOME!")
                .alarmContent("SSE CONNECTED!")
                .alarmRedirect("")
                .build());

        emitter.onCompletion(() -> {
            log.info("onCompletion callback");
            this.emitterMap.remove(userId);
        });
        emitter.onTimeout(() -> {
            log.info("onTimeout callback");
            emitter.complete();
        });
//                emitterMap.remove(userId));
//        emitter.onError((e) -> emitterMap.remove(userId));

        log.info("SSE 구독 요청 완료: {} (스레드: {})", userId, Thread.currentThread().getName());
        return emitter;
    }

    @Async("notiExecutor")
    public void sendAsync(AlarmPayload alarmPayload) {
        String userId = alarmPayload.getReceiveUserId().toString();
        log.info("비동기 메서드 시작: 사용자 {}, 데이터 {} (스레드: {})", userId, alarmPayload, Thread.currentThread().getName());
        SseEmitter emitter = emitterMap.get(userId);

        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().data(alarmPayload));
                log.info("서버로부터 SSE 전송 성공: 사용자 {}, 데이터 {}", userId, alarmPayload);
            } catch (IOException e) {
                log.error("SSE 전송 실패: 사용자 {}, 오류 메시지 {}", userId, e.getMessage(), e);
                emitter.completeWithError(e);
                emitterMap.remove(userId);
            }
        } else {
            log.warn("SSE 전송 시도 실패: 사용자 {}에 대한 Emitter 없음", userId);
        }

        log.info("비동기 메서드 종료: 사용자 {} (스레드: {})", userId, Thread.currentThread().getName());
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
