package com.ssafy.fullerting.alarm.controller;

import com.ssafy.fullerting.alarm.service.EventAlarmService;
import com.ssafy.fullerting.global.utils.MessageUtils;
import com.ssafy.fullerting.user.model.dto.request.UserRegisterRequest;
import com.ssafy.fullerting.user.service.UserService;
import jakarta.validation.Valid;
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

}
