package com.ssafy.fullerting.global.fcm.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class MessageRequestDTO {
    private String title;
    private String body;
    private String targetToken;
}
