package com.ssafy.fullerting.security.service;

import com.ssafy.fullerting.security.exception.AuthErrorCode;
import com.ssafy.fullerting.security.exception.AuthException;
import com.ssafy.fullerting.security.exception.JwtErrorCode;
import com.ssafy.fullerting.security.exception.JwtException;
import com.ssafy.fullerting.security.model.dto.response.IssuedToken;
import com.ssafy.fullerting.security.model.entity.CustomAuthenticationToken;
import com.ssafy.fullerting.security.model.entity.InvalidToken;
import com.ssafy.fullerting.security.model.entity.Token;
import com.ssafy.fullerting.security.repository.InvalidTokenRepository;
import com.ssafy.fullerting.security.repository.TokenRepository;
import com.ssafy.fullerting.security.util.JwtUtils;
import com.ssafy.fullerting.user.model.entity.MemberProfile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtUtils jwtUtils;
    private final TokenRepository tokenRepository;
    private final InvalidTokenRepository invalidTokenRepository;
    private final DataBaseUserDetailsService dataBaseUserDetailsService;
    private final RedisTemplate<String, String> redisTemplate;

    public IssuedToken issueToken(Authentication authentication) {

        String accessToken;
        String refreshToken;
        Long userId = null;
        String email = null;

        // 인증 방식에 따라 이메일 추출
        if (authentication instanceof CustomAuthenticationToken) {
            CustomAuthenticationToken customAuth = (CustomAuthenticationToken) authentication;
            email = customAuth.getPrincipal().toString();
        }

        else if (authentication.getPrincipal() instanceof OAuth2User) {
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
            email = oAuth2User.getAttribute("email");
        }

        // 이메일 사용해서 사용자 정보 조회
        if (email != null) {
            try {
                MemberProfile customUser = dataBaseUserDetailsService.loadUserByUsername(email);
                userId = customUser.getId();
            } catch (UsernameNotFoundException e) {
                throw new AuthException(AuthErrorCode.NOT_EXISTS);
            }
        }
        // 사용자권한 추출
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        accessToken = jwtUtils.issueAccessToken(email, userId, authorities);
        refreshToken = jwtUtils.issueRefreshToken(email, userId, authorities);
        log.info("before redissave" + refreshToken.toString());
        // Redis에 토큰 저장
        tokenRepository.save(new Token(userId, refreshToken));
        return new IssuedToken(accessToken, refreshToken);

    }

    public void removeAccessToken(String accessToken) {
        // 로그아웃시 유효한 access 토큰을
        // redis에 blacklist로 보내야함
        jwtUtils.validateAccessToken(accessToken);
        invalidTokenRepository.save(new InvalidToken(accessToken));
    }

    // 유효기간 지난 access 토큰 refresh 토큰과 비교해서 재발행
    public IssuedToken reIssueAccessTokenByRefreshToken(String refreshToken) {
        // 미구현
        // EXPIRED_TOKEN
        log.info("reIssueAccessTokenByRefreshToken");
        if (jwtUtils.validateRefreshToken(refreshToken)) {
            Long userId = jwtUtils.getUserIdByRefreshToken(refreshToken);
            String userEmail = jwtUtils.getEmailByRefreshToken(refreshToken);
            List<String> userRole = jwtUtils.getRoleByRefreshToken(refreshToken);

            // 리프레쉬 토큰에 있는 userId로 DB에 저장된 토큰 refresh token 있는지 조회
            Token tokenInDB = tokenRepository.findById(userId)
                    .orElseThrow(() -> new JwtException(JwtErrorCode.NOT_EXISTS_TOKEN));
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            // 지금 리프레쉬 토큰하고 DB에 저장된 리프레쉬토큰하고 같다면
            // 새로운 어세스 토큰과 리프레쉬 토큰 재발급
            if (refreshToken.equals(tokenInDB.getRefreshToken())) {
                // 사용자 권한을 SimpleGrantedAuthority 리스트로 변환
                List<SimpleGrantedAuthority> authorities = userRole.stream()
                        .map(SimpleGrantedAuthority::new)
                        .toList();

                // 새 토큰 발급 (Refresh-Token Rotation)
                String newAccessToken = jwtUtils.issueAccessToken(userEmail, userId, authorities);
                String newRefreshToken = jwtUtils.issueRefreshToken(userEmail, userId, authorities);

                // Redis 갱신 – 기존 레코드 덮어쓰기 (old refreshToken → invalid 저장 선택)
                tokenRepository.save(new Token(userId, newRefreshToken));

                // 보안을 위해 기존 refreshToken 은 블랙리스트에 보관 (선택)
                invalidTokenRepository.save(new InvalidToken(null, refreshToken));

                log.info("[TOKEN-ROTATE] userId={} access={} refresh={}", userId, newAccessToken, newRefreshToken);
                return new IssuedToken(newAccessToken, newRefreshToken);
            } else {
                // 두 리프레쉬 토큰이 다르다면
                // 해당 토큰은 유요하지 않다고 판단하고 따로 저장
                // 이후 예외처리
                // invalidTokenRepository.save(new InvalidToken(tokenInDB.getAccessToken(),
                // tokenInDB.getRefreshToken()));
                throw new JwtException(JwtErrorCode.INVALID_TOKEN);
            }
        }
        throw new JwtException(JwtErrorCode.INVALID_TOKEN);
    }

    public void addToBlacklist(String token) {
        // 블랙리스트에 토큰 추가 (예: 만료된 토큰)
        redisTemplate.opsForValue().set(token, "blacklisted", 7, TimeUnit.DAYS);
    }

    public boolean isBlacklisted(String token) {
        // 블랙리스트에 토큰이 있는지 확인
        return redisTemplate.hasKey(token);
    }
}
