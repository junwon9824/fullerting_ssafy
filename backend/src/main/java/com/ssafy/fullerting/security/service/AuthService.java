package com.ssafy.fullerting.security.service;

import com.ssafy.fullerting.security.config.CustomAuthenticationProvider;
import com.ssafy.fullerting.security.exception.AuthErrorCode;
import com.ssafy.fullerting.security.exception.AuthException;
import com.ssafy.fullerting.security.model.dto.request.LoginRequest;
import com.ssafy.fullerting.security.model.dto.response.IssuedToken;
import com.ssafy.fullerting.user.exception.UserErrorCode;
import com.ssafy.fullerting.user.exception.UserException;
import com.ssafy.fullerting.user.model.entity.CustomUser;
import com.ssafy.fullerting.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class AuthService {

    private final DataBaseUserDetailsService dataBaseUserDetailsService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public IssuedToken login(LoginRequest loginRequest) {
        // 사용자가 입력한 정보
        String inputEmail = loginRequest.getEmail();
        String inputPassword = loginRequest.getPassword();

        try {
            // 인증 로직을 거친 뒤 인증 객체 생성
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(inputEmail, inputPassword));
            // 해당 인증객체를 ContextHolder에 등록
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (AuthenticationException e) {
            throw new UserException(UserErrorCode.ACCESS_DENIED);
        }


        // 사용자가 입력한 정보를 바탕으로 DB에서 데이터 찾고 없으면 오류 반환


        // 같으면 토큰 반환
//        IssuedToken issuedToken = tokenService.issueToken(userId, userEmail, userRole);
//        log.info("[Login] UserId:{}, userEmail:{}, userRole:{}, token:{} ", userId, userEmail, userRole, issuedToken);
//        return issuedToken;
//        }
        throw new AuthException(AuthErrorCode.NOT_EXISTS);
    }

    public void logout(CustomUser customUser) {
        tokenService.removeToken(customUser.getId());
    }

    // SecurityContext에 저장된 auth 객체 조조히
    public Authentication getAuthenticationInContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("현재 쓰레드 Context에 저장된 Authentication : {} ", authentication.toString());
        log.info("현재 쓰레드 Context에 저장된 principal : {} ", authentication.getName());
        return authentication;
    }
    public IssuedToken refresh(String refreshToken) {
        return tokenService.reIssueToken(refreshToken);
    }

}
