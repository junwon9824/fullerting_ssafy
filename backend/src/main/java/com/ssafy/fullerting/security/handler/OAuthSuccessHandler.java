package com.ssafy.fullerting.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.fullerting.global.utils.MessageUtils;
import com.ssafy.fullerting.security.model.dto.response.IssuedToken;
import com.ssafy.fullerting.security.service.TokenService;
import com.ssafy.fullerting.user.model.entity.CustomUser;
import com.ssafy.fullerting.user.repository.UserRepository;
import com.ssafy.fullerting.user.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final UserRepository userRepository;
    private final UserService userService;
    private final TokenService tokenService;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws  IOException, ServletException {
        //http://localhost:8080/login 에서 확인
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        log.info("Oauth 사용자 정보 확인 : {}", oAuth2User.toString());

        // 리디렉션할 URL과 토큰 발급
        String redirectUrl = "https://j10c102.p.ssafy.io/auth/callback";
        try {
            userRepository.findByEmail(oAuth2User.getName()).ifPresentOrElse(
                    user -> {
                        // 사용자가 이미 존재하는 경우
                        try {
                            redirectToCallbackWithToken(response, tokenService.issueToken(authentication), redirectUrl);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    },
                    () -> {
                        // 새로운 사용자 등록
                        registerNewUser(oAuth2User);
                        try {
                            redirectToCallbackWithToken(response, tokenService.issueToken(authentication), redirectUrl);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
            );
        } catch (Exception e) {
            log.error("인증 성공 후 처리 중 오류 발생", e);
        }
    }

    private void registerNewUser(OAuth2User oAuth2User) {
        // 새 사용자 등록 로직
        CustomUser customUser = CustomUser.of(oAuth2User);
        customUser.setPassword("DummyPasswordeoar!@3");
        userService.registOauthUser(customUser);
    }

    private void redirectToCallbackWithToken(HttpServletResponse response, IssuedToken issuedToken, String redirectUrl) throws IOException {
        String urlWithToken = String.format("%s?accessToken=%s&refreshToken=%s",
                redirectUrl,
                URLEncoder.encode(issuedToken.getAccessToken(), StandardCharsets.UTF_8),
                URLEncoder.encode(issuedToken.getRefreshToken(), StandardCharsets.UTF_8));
        response.sendRedirect(urlWithToken);
    }



    // JSON 생성
    private void writeResponse(HttpServletResponse response, Object tokenResponse) {
        try {
            String json = objectMapper.writeValueAsString(tokenResponse);
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(json);
        } catch (IOException e) {
            log.error("Response write error", e);
        }
    }
}