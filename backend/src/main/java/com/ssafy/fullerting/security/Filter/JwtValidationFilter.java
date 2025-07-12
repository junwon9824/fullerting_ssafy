package com.ssafy.fullerting.security.Filter;

import com.ssafy.fullerting.security.model.entity.CustomAuthenticationToken;
import com.ssafy.fullerting.security.util.JwtUtils;
import com.ssafy.fullerting.user.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtValidationFilter extends OncePerRequestFilter {
    private final MemberRepository userRepository;
    private final JwtUtils jwtUtils;
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);

        // Bearer 파싱을 더 견고하게
        String accessToken = null;
        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith(BEARER_PREFIX)) {
            accessToken = authorizationHeader.substring(BEARER_PREFIX.length()).trim();
        }

        if (!StringUtils.hasText(accessToken)) {
            // 토큰이 없으면 인증 없이 다음 필터로 넘김
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // 엑세스 토큰 검증
            Jws<Claims> claimsJws = jwtUtils.validateAccessToken(accessToken);

            if (claimsJws != null) {
                List<String> roles = claimsJws.getBody().get("authorities", List.class);
                Collection<GrantedAuthority> authorities = roles.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

                CustomAuthenticationToken customAuthenticationToken = new CustomAuthenticationToken(
                        jwtUtils.getEmailByAccessToken(accessToken),
                        jwtUtils.getUserIdByAccessToken(accessToken),
                        null,
                        authorities);

                SecurityContextHolder.getContext().setAuthentication(customAuthenticationToken);
                log.info("jwt 토큰 검증 성공 : {}", SecurityContextHolder.getContext().toString());
            }
        } catch (Exception e) {
            // 검증 실패 시 401 반환 및 로그
            log.warn("JWT 토큰 검증 실패: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"error\": \"Invalid or expired JWT token.\"}");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
