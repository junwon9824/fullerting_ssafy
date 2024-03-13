package com.ssafy.fullerting.security.service;

import com.ssafy.fullerting.security.exception.AuthErrorCode;
import com.ssafy.fullerting.security.exception.AuthException;
import com.ssafy.fullerting.security.model.dto.request.LoginRequest;
import com.ssafy.fullerting.security.model.dto.response.IssuedToken;
import com.ssafy.fullerting.user.exception.UserErrorCode;
import com.ssafy.fullerting.user.exception.UserException;
import com.ssafy.fullerting.user.model.entity.User;
import com.ssafy.fullerting.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public IssuedToken login(LoginRequest loginRequest) {
        // 사용자가 입력한 정보
        String inputEmail = loginRequest.getEmail();
        String inputPassword = loginRequest.getPassword();

        // 사용자가 입력한 정보를 바탕으로 DB에서 데이터 찾고 없으면 오류 반환
        User user = userRepository.findByEmail(inputEmail).orElseThrow(() -> new UserException(UserErrorCode.NOT_EXISTS_USER));

        // 유저가 입력한 패스워드와 DB에 있는 정보 비교 (loadByUsername)
        if (passwordEncoder.matches(inputPassword, user.getPassword())) {
            Long userId = user.getId();
            String userEmail = user.getEmail();
            String userRole = user.getRole();
            // 같으면 토큰 반환
            IssuedToken issuedToken = tokenService.issueToken(userId, userEmail, userRole);
            log.info("[Login] UserId:{}, userEmail:{}, userRole:{}, token:{} ", userId, userEmail, userRole, issuedToken);

            return issuedToken;
        }
        throw new AuthException(AuthErrorCode.NOT_EXISTS);
    }


    public void logout(User user) {
        tokenService.removeToken(user.getId());
    }

    public IssuedToken refresh(String refreshToken) {
        return tokenService.reIssueToken(refreshToken);
    }

}
