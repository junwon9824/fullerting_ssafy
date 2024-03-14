package com.ssafy.fullerting.security.config;

import com.ssafy.fullerting.security.service.DataBaseUserDetailsService;
import com.ssafy.fullerting.user.model.entity.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final PasswordEncoder passwordEncoder;
    private final DataBaseUserDetailsService dataBaseUserDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 인증 로직 정의
        String inputUsername = authentication.getName();
        String inputPassword = authentication.getCredentials().toString();

        UserDetails userDetails = dataBaseUserDetailsService.loadUserByUsername(inputUsername);

        // 유저가 입력한 패스워드와 DB에 있는 정보 비교 (자격 검정)
        if (passwordEncoder.matches(inputPassword, userDetails.getPassword())) {
            return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()); // 비밀번호는 이제 전달x
        } else {
            throw new BadCredentialsException(inputUsername + "의 비밀번호가 아님");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // 인증 객체의 검증 방식 정의
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
