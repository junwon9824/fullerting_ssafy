package com.ssafy.fullerting.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        //http://localhost:8080/login 에서 확인
        log.info("Oauth 사용자 정보 확인 : {}", oAuth2User.toString());

//        CustomUser oauth2user = CustomUser.of(oAuth2User);
        // OAuth2 인증으로 얻은 사용자 정보를 바탕으로 CustomUser 객체 생성
//        response.setContentType("application/json;charset=UTF-8");
//        response.setStatus(HttpServletResponse.SC_OK);

        // OAuth2 인증으로 얻은 사용자 정보를 바탕으로 CustomUser 객체 생성


//        // DB에 유저 있는지 조회
//        Optional<CustomUser> optionalUser = userRepository.findByEmail(oauth2user.getEmail());
//
//        if (optionalUser.isPresent()){
//            //로그인인 경우
//            CustomUser user = optionalUser.get();
//            response.getWriter().write(objectMapper.writeValueAsString(
//                    MessageUtils.success(authService.login(new LoginRequest(user.getEmail(), user.getPassword())))));
//        }
//        else {
//            //회원가입일 경우
//            CustomUser saveUser = userRepository.save(oauth2user);
//            response.getWriter().write(objectMapper.writeValueAsString(
//                    MessageUtils.success(authService.login(new LoginRequest(saveUser.getEmail(), saveUser.getPassword())))));
//        }
    }
}