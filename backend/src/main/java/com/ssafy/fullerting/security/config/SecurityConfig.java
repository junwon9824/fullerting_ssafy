package com.ssafy.fullerting.security.config;


import com.ssafy.fullerting.security.Filter.JwtFilter;
import com.ssafy.fullerting.security.handler.AuthFailureHandler;
import com.ssafy.fullerting.security.handler.ExceptionHandlerFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final AuthFailureHandler authFailureHandler;
    private final ExceptionHandlerFilter exceptionHandlerFilter;

    // 패스워드 암호화 방식
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // cors 설정
                .cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfig()))
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)

                // 인가 경로 설정
                .authorizeHttpRequests((requests) ->
                        requests.requestMatchers(
                                "/v1/auth/login",
                                "/v1/users/register",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-resources/**"
                        ).permitAll().anyRequest().authenticated())
                // 예외처리
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling
                                .authenticationEntryPoint(authFailureHandler)
                )
                // JWT 필터
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(exceptionHandlerFilter, JwtFilter.class);

        return http.build();
    }

    // CORS 설정
    public CorsConfigurationSource corsConfig() {
        return request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedHeaders(Collections.singletonList("*"));
            config.setAllowedMethods(Collections.singletonList("*"));
            config.setAllowedOrigins(Collections.singletonList("*")); // 모든 Origin 허용
            config.setAllowCredentials(false); // 모든 도메인을 허용할 때는 false로 설정해야 함
            config.setMaxAge(3600L);
            return config;
        };
    }
}
