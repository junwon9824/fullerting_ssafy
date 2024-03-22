package com.ssafy.fullerting.global.fcm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
public class FcmSendDto {
    private String token;

    private String title;

    private String body;

    @Builder(toBuilder = true)
    public FcmSendDto(String token, String title, String body) {
        this.token = token;
        this.title = title;
        this.body = body;
    }
}
