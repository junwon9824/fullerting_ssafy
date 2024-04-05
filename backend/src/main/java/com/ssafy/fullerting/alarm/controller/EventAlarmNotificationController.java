package com.ssafy.fullerting.alarm.controller;

import com.ssafy.fullerting.alarm.model.dto.request.AlarmPayload;
import com.ssafy.fullerting.alarm.service.EventAlarmNotificationService;
import com.ssafy.fullerting.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/noti")
public class EventAlarmNotificationController {
    private final UserService userService;
    private final EventAlarmNotificationService eventAlarmNotificationService;

    //응답 mime type 은 반드시 text/event-stream 이여야 한다.
    //클라이언트로 부터 SSE subscription 을 수락한다.
    @GetMapping(path = "/pub", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public SseEmitter subscribe() {
        return eventAlarmNotificationService.subscribe(userService.getUserInfo().getId());
    }

    @PostMapping(path = "/send")
    public ResponseEntity<Void> sendToUser(@RequestBody AlarmPayload alarmPayload) {
        eventAlarmNotificationService.sendAsync(alarmPayload);
        return ResponseEntity.ok().build();
    }

}
