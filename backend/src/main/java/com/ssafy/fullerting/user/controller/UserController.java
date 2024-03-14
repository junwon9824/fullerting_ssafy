package com.ssafy.fullerting.user.controller;

import com.ssafy.fullerting.global.utils.MessageUtils;
import com.ssafy.fullerting.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
@Tag(name = "유저 기능 API", description = "유저와 관련된 기능 제공")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    @Operation(summary = "유저 회원가입", description = "이메일, 비밀번호, 닉네임을 입력받아 회원가입을 진행한다")
    public ResponseEntity<MessageUtils> register(@Valid @RequestBody UserRegisterRequest userRegisterRequest) {
        userService.registerUser(userRegisterRequest);
        log.info("[New User]: {}", userRegisterRequest.toString());
        return ResponseEntity.ok().body(MessageUtils.success());
    }
}
