package com.ssafy.fullerting.security.service;

import com.ssafy.fullerting.security.model.dto.request.LoginRequest;
import com.ssafy.fullerting.security.model.dto.response.IssuedToken;
import com.ssafy.fullerting.security.model.entity.CustomAuthenticationToken;
import com.ssafy.fullerting.user.exception.UserErrorCode;
import com.ssafy.fullerting.user.exception.UserException;
import com.ssafy.fullerting.user.model.entity.CustomUser;
import com.ssafy.fullerting.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    public IssuedToken login(LoginRequest loginRequest) {
        // 사용자가 입력한 정보
        String inputEmail = loginRequest.getEmail();
        String inputPassword = loginRequest.getPassword();

        try {
            // CustomAuthenticationProvider를 호출해 인증로직을 수행한다
            // 인증에 성공하면 인증에 성공한 인증 객체를 리턴한다
            Authentication authentication =
                    authenticationManager.authenticate(new CustomAuthenticationToken(inputEmail, inputPassword));
            // 성공한 인증객체를 ContextHolder에 등록
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("로그인 성공 객체정보 : {} ", authentication.toString());

            // 해당 인증 객체를 바탕으로 토큰을 발급한다
            IssuedToken issuedToken = tokenService.issueToken(authentication);
            return issuedToken;

        } catch (AuthenticationException e) {
            throw new UserException(UserErrorCode.ACCESS_DENIED);
        }
    }

    public void logout() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = (String) principal;

        CustomUser customUser = userRepository.findByEmail(email).orElseThrow(() -> new UserException(UserErrorCode.NOT_EXISTS_USER));

        tokenService.removeAccessToken(customUser.getId());
        SecurityContextHolder.clearContext();
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
