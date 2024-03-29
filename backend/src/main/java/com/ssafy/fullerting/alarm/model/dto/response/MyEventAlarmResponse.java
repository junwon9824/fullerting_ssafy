package com.ssafy.fullerting.alarm.model.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MyEventAlarmResponse {
    private String alarmType;
    private String alarmContent;
    private String alarmRedirect;
    private boolean isChecked;
}
