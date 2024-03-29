package com.ssafy.fullerting.alarm.controller;

import com.ssafy.fullerting.alarm.service.EventAlarmService;
import com.ssafy.fullerting.global.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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

}
