package com.ssafy.fullerting.alarm.model.dto.request;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AlarmPayload {
    private Long receiveUserId;
    private Long alarmId;
    private String alarmType;
    private String alarmContent;
    private String alarmRedirect;
}
