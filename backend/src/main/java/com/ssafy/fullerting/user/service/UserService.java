package com.ssafy.fullerting.user.service;

import com.ssafy.fullerting.global.s3.servcie.AmazonS3Service;
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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final AmazonS3Service amazonS3Service;

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
        return customUser.toResponse();
    }


    @Transactional
    public UserResponse uploadThumbAndSaveDB(MultipartFile multipartFile) {
        // 현재 인증된 유저의 db정보를 불러온다
        CustomUser user = userRepository.findByEmail(getUserInfo().getEmail()).orElseThrow(() -> new UserException(UserErrorCode.NOT_EXISTS_USER));

        // 기존에 프로필 사진이 있었으면 지운다.
        String thumbnail = user.getThumbnail();
        if (thumbnail != null) {
            amazonS3Service.deleteFile(thumbnail);
        }

        // 사진을 받아 S3 에 저장하고
        String s3URL = amazonS3Service.uploadThunmail(multipartFile).getUrl();
        // S3 객체 url을 해당 유저 db에 저장한다
        user.setThumbnail(s3URL);
        userRepository.save(user);

        // 이후 유저 정보를 리턴
        return user.toResponse();
    }


}
