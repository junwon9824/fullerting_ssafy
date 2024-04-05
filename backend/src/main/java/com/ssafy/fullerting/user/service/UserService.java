package com.ssafy.fullerting.user.service;

import com.ssafy.fullerting.global.s3.servcie.AmazonS3Service;
import com.ssafy.fullerting.user.exception.UserErrorCode;
import com.ssafy.fullerting.user.exception.UserException;
import com.ssafy.fullerting.user.model.dto.request.UserRegisterRequest;
import com.ssafy.fullerting.user.model.dto.request.UserTownRequest;
import com.ssafy.fullerting.user.model.dto.request.UserUpdateRequest;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AmazonS3Service amazonS3Service;
    String defaultThunmail = "https://fullerting-s3-v2.s3.ap-northeast-2.amazonaws.com/default_thumnail.svg";

    public CustomUser createUserEntity(UserRegisterRequest userRegisterRequest) {
        return CustomUser.builder()
                .email(userRegisterRequest.getEmail())
                .password(passwordEncoder.encode(userRegisterRequest.getPassword()))
                .thumbnail(defaultThunmail)
                .nickname(userRegisterRequest.getNickname())
                .role(String.valueOf(UserRole.ROLE_MEMBER))
                .rank(String.valueOf(UserRank.새싹))
                .authProvider(userRegisterRequest.getAuthProvider())
                .build();
    }

    public void registUser(UserRegisterRequest request) {
        // 등록하려는 유저정보가 이미 DB에 있으면 예외처리
        userRepository.findByEmail(request.getEmail()).ifPresent(u -> {
            throw new UserException(UserErrorCode.ALREADY_IN_EMAIL);
        });
        // 유저 객체를 DB에 저장
        userRepository.save(createUserEntity(request));
    }


    public void registOauthUser(CustomUser customUser) {
        try {
            userRepository.save(customUser);
        } catch (RuntimeException e) {
            throw new RuntimeException("오류 발생 : " + e);
        }
        log.info("유저 회원가입 성공 : {}", customUser.toString());

    }

    public UserResponse getUserInfo() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = (String) principal;

        CustomUser customUser = userRepository.findByEmail(email).orElseThrow(() -> new UserException(UserErrorCode.NOT_EXISTS_USER));
        return customUser.toResponse();
    }

    public CustomUser getNowUserInfoEntityByToken() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = (String) principal;

        CustomUser customUser = userRepository.findByEmail(email).orElseThrow(() -> new UserException(UserErrorCode.NOT_EXISTS_USER));
        return customUser;
    }


    @Transactional
    public UserResponse uploadThumbAndSaveDB(MultipartFile multipartFile) {
        // 현재 인증된 유저의 db정보를 불러온다
        CustomUser user = userRepository.findByEmail(getUserInfo().getEmail()).orElseThrow(() -> new UserException(UserErrorCode.NOT_EXISTS_USER));

        // 기존에 프로필 사진이 있었으면 지운다.
        // 기본 사진일 경우 S3에서 지우지 않는다
        String thumbnail = user.getThumbnail();
        if (thumbnail != null && !thumbnail.equals(defaultThunmail)) {
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


    public UserResponse getUserInfobyid(Long userid) {

        CustomUser customUser = userRepository.findById(userid).orElseThrow(() -> new UserException(UserErrorCode.NOT_EXISTS_USER));
        return customUser.toResponse();
    }

    public CustomUser getUserEntityById(Long userId) {
        CustomUser customUser = userRepository.findById(userId).orElseThrow(() -> new UserException(UserErrorCode.NOT_EXISTS_USER));
        return customUser;
    }

    public void updatetown(UserTownRequest userTownRequest) {
        CustomUser user = userRepository.findByEmail(getUserInfo().getEmail()).orElseThrow(() -> new UserException(UserErrorCode.NOT_EXISTS_USER));
        user.setLocation(userTownRequest.getUserLocation());
        userRepository.save(user);
    }


    @Transactional
    public void updateCurrentUserInfo(UserUpdateRequest userUpdateRequest) {
        CustomUser currentUser = getNowUserInfoEntityByToken();
        currentUser.setNickname(userUpdateRequest.getNewNickname());
        userRepository.save(currentUser);
        log.info("닉네임 수정 성공 : {}",userUpdateRequest.getNewNickname());
    }
}
