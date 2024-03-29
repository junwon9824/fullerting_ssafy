package com.ssafy.fullerting.alarm.controller;

import com.ssafy.fullerting.alarm.service.EventAlarmService;
import com.ssafy.fullerting.global.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/alarms")
public class EventAlarmController {
    private final EventAlarmService eventAlarmService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<MessageUtils> getAlarmsForUser() {
        return ResponseEntity.ok().body(MessageUtils.success(eventAlarmService.getEventAlarmsForUser()));
    }

    @PostMapping("/{alarmId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<MessageUtils> alarmReadCheck(@PathVariable Long alarmId) {
        eventAlarmService.markAlarmAsRead(alarmId);
        return ResponseEntity.ok().body(MessageUtils.success());
    }

}
