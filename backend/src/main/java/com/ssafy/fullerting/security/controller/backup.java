//package com.ssafy.fullerting.security.handler;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.ssafy.fullerting.global.utils.MessageUtils;
//import com.ssafy.fullerting.security.service.TokenService;
//import com.ssafy.fullerting.user.model.entity.CustomUser;
//import com.ssafy.fullerting.user.repository.UserRepository;
//import com.ssafy.fullerting.user.service.UserService;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
//@Slf4j
//@RequiredArgsConstructor
//@Component
//public class OAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
//    private final UserRepository userRepository;
//    private final UserService userService;
//    private final TokenService tokenService;
//    private final ObjectMapper objectMapper;
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        //http://localhost:8080/login 에서 확인
//        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
//        CustomUser customUser = CustomUser.of(oAuth2User);
//        log.info("Oauth 사용자 정보 확인 : {}", oAuth2User.toString());
//
////        response.setContentType("application/json;charset=UTF-8");
////        response.setStatus(HttpServletResponse.SC_OK);
//
////        // DB에 유저 있는지 조회
//        userRepository.findByEmail(oAuth2User.getName()).ifPresentOrElse(user -> {
//            try {
//                response.getWriter().write(objectMapper.writeValueAsString(
//                        MessageUtils.success(tokenService.issueToken(authentication))));
//
//            } catch (IOException e) {
//                log.error("Response write error", e);
//            }
//        }, () -> {
//            try {
//                // OAuth2 인증으로 얻은 사용자 정보를 바탕으로 CustomUser 객체 생성
//                customUser.setPassword("DummyPasswordeoar!@3");
//                userService.registOauthUser(customUser);
//                log.info("OAuth 유저 회원가입 성공 : {}", customUser.toString());
//
//                response.getWriter().write(objectMapper.writeValueAsString(
//                        MessageUtils.success(tokenService.issueToken(authentication))));
//
//            } catch (IOException e) {
//                log.error("Response write error", e);
//            }
//        });
//    }
//
//    private void writeResponse(HttpServletResponse response, Object tokenResponse) {
//        try {
//            String json = objectMapper.writeValueAsString(tokenResponse);
//            response.setContentType("application/json;charset=UTF-8");
//            response.setStatus(HttpServletResponse.SC_OK);
//            response.getWriter().write(json);
//        } catch (IOException e) {
//            log.error("Response write error", e);
//        }
//    }
//}