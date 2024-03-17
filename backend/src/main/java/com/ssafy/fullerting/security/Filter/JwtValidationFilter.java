package com.ssafy.fullerting.security.Filter;

import com.ssafy.fullerting.security.model.entity.CustomAuthenticationToken;
import com.ssafy.fullerting.security.util.JwtUtils;
import com.ssafy.fullerting.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.br.TituloEleitoral;
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
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);

        //토큰이 없는경우
        if(!StringUtils.hasText(authorizationHeader)){
            doFilter(request, response, filterChain);
            return;
        }
        //Bear로 시작하지 않는 경우
        if(!authorizationHeader.startsWith(BEARER_PREFIX)){
            doFilter(request, response, filterChain);
            return;
        }
        //jwt추출
        String accessToken = authorizationHeader.split(" ")[1];

        //엑세스 토큰 검증
        Jws<Claims> claimsJws = jwtUtils.validateAccessToken(accessToken);

        //토큰 있으면 검증
        if (claimsJws != null) {
//            CustomUser customUser = userRepository.findById(claimsJws.getBody().get("userId", Long.class))
//                    .orElseThrow(() -> new UserException(UserErrorCode.NOT_EXISTS_USER));
//
            // 각 권한 문자열을 SimpleGrantedAuthority 객체로 변환
            List<String> roles = claimsJws.getBody().get("authorities", List.class);
            Collection<GrantedAuthority> authorities = roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            CustomAuthenticationToken customAuthenticationToken =
                    new CustomAuthenticationToken(
                            jwtUtils.getEmailByAccessToken(accessToken),
                            jwtUtils.getUserIdByAccessToken(accessToken),
                            null,
                            authorities
                    ); // principal,userid,password,authorities 가 들어감}
            SecurityContextHolder.getContext().setAuthentication(customAuthenticationToken);
            log.info("jwt 토큰 검증 : {}", SecurityContextHolder.getContext().toString());
        }

//        boolean authenticated = SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
//
//        if(claimsJws != null && authenticated){
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            CustomAuthenticationToken auth = (CustomAuthenticationToken) authentication;
//
//            // 현재 인증 사용자랑 토큰 principal이랑 같은지 확인
//            if (claimsJws.getBody().getSubject().equals(auth.getPrincipal())) {
//                log.info("인증 객체와 토큰 동일 확인 : {}", claimsJws.getBody().getSubject());
//
//                // 같으면 새로 인증객체 생성(동기화)
//                CustomAuthenticationToken customAuthenticationToken =
//                        new CustomAuthenticationToken(auth.getPrincipal(),
//                                auth.getUserId(),
//                                null,
//                                auth.getAuthorities()
//                        ); // principal,userid,password,authorities 가 들어감
//                SecurityContextHolder.getContext().setAuthentication(customAuthenticationToken);
//            } else {
//                log.info("인증 객체 : {} 와 토큰 정보 : {} 불일치 -> 새로 로그인 필요" , auth.getPrincipal() , claimsJws.getBody().getSubject());
//            }

//            CustomUser customUser = userRepository.findById(claimsJws.getBody().get("userId", Long.class))
//                    .orElseThrow(() -> new UserException(UserErrorCode.NOT_EXISTS_USER));
//        }

        filterChain.doFilter(request, response);
    }
}
