package com.ssafy.fullerting.security.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@AllArgsConstructor
@Builder
@RedisHash(value = "blackList", timeToLive = 86400)
public class InvalidToken {
    @Id
    private String accessToken;
    private String refreshToken;
}
