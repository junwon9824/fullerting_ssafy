package com.ssafy.fullerting.security.controller;

import com.ssafy.fullerting.global.utils.MessageUtils;
import com.ssafy.fullerting.security.model.dto.request.LoginRequest;
import com.ssafy.fullerting.security.service.AuthService;
import com.ssafy.fullerting.user.model.dto.request.RefreshRequest;
import com.ssafy.fullerting.user.model.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
@Tag(name="회원 기능 API", description = "인가,인증과 관련된 작업을 수행한다")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary="회원 로그인", description="이메일, 비밀번호로 로그인한다")
    public ResponseEntity<MessageUtils> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok().body(MessageUtils.success(authService.login(loginRequest)));
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary="회원 로그아웃", description="로그인 상태에서 로그아웃한다. <br> [헤더 Bearer: Access토큰 필요] <br> 토큰을 기반으로 유저정보 검사")
    public ResponseEntity<MessageUtils> logut(@AuthenticationPrincipal User user) {
        authService.logout(user);
        return ResponseEntity.ok().body(MessageUtils.success());
    }

    @PostMapping("/refresh")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary="토큰 재발행", description="REFRESH 토큰을 비교해 기한이 만료된 ACCESS 토큰을 재발행한다 <br> REFRESH 토큰의 유효기간이 남았을 경우만 재발급한다")
    public ResponseEntity<MessageUtils> refresh(@RequestBody RefreshRequest refreshRequest) {
        return ResponseEntity.ok().body(MessageUtils.success(authService.refresh(refreshRequest.getRefreshToken())));
    }

}
