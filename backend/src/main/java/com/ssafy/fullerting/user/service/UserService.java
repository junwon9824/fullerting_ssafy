package com.ssafy.fullerting.user.service;

import com.ssafy.fullerting.security.exception.JwtErrorCode;
import com.ssafy.fullerting.security.exception.JwtException;
import com.ssafy.fullerting.security.repository.TokenRepository;
import com.ssafy.fullerting.user.exception.UserErrorCode;
import com.ssafy.fullerting.user.exception.UserException;
import com.ssafy.fullerting.user.model.dto.request.UserRegisterRequest;
import com.ssafy.fullerting.user.model.dto.response.UserResponse;
import com.ssafy.fullerting.user.model.entity.CustomUser;
import com.ssafy.fullerting.user.model.entity.enums.UserRank;
import com.ssafy.fullerting.user.model.entity.enums.UserRole;
import com.ssafy.fullerting.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;

    public CustomUser createUserEntity(UserRegisterRequest userRegisterRequest) {
        String inputEmail = userRegisterRequest.getEmail();
        String inputPassword = userRegisterRequest.getPassword();
        String inputNickname = userRegisterRequest.getNickname();

        return CustomUser.builder()
                .email(inputEmail)
                .password(passwordEncoder.encode(inputPassword))
                .nickname(inputNickname)
                .role(String.valueOf(UserRole.ROLE_MEMBER))
                .rank(String.valueOf(UserRank.새싹))
                .build();
    }

    public void registerUser(UserRegisterRequest request) {
        // 등록하려는 유저정보가 이미 DB에 있으면 예외처리
        userRepository.findByEmail(request.getEmail()).ifPresent(u -> {
            throw new UserException(UserErrorCode.ALREADY_IN_EMAIL);
        });
        // 유저 객체를 DB에 저장
        userRepository.save(createUserEntity(request));
    }

    public UserResponse getUserInfo() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = (String) principal;

        CustomUser customUser = userRepository.findByEmail(email).orElseThrow(() -> new UserException(UserErrorCode.NOT_EXISTS_USER));
        tokenRepository.findById(customUser.getId()).orElseThrow(() -> new JwtException(JwtErrorCode.NOT_EXISTS_TOKEN));
        return customUser.toResponse();
    }




}
