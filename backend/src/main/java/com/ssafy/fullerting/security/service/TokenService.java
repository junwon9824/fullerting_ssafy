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
import com.ssafy.fullerting.user.model.entity.CustomUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtUtils jwtUtils;
    private final TokenRepository tokenRepository;
    private final InvalidTokenRepository invalidTokenRepository;
    private final DataBaseUserDetailsService dataBaseUserDetailsService;

    public IssuedToken issueToken(Authentication authentication) {
        String accessToken;
        String refreshToken;

        Long userId;
        Object principal = authentication.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        if (authentication instanceof CustomAuthenticationToken) {
            // 인증 객체에 유저 아이디를 담아서 보내는 경우, 인증 객체에서 바로 뽑아서 토큰 생성
            CustomAuthenticationToken customAuth = (CustomAuthenticationToken) authentication;
            userId = customAuth.getUserId();
        } else {
            // 인증 객체에 유저 아이디가 없는 일반적인 경우, DB에서 유저 아이디 조회 후 토큰 생성
            try {
                CustomUser customUser = dataBaseUserDetailsService.loadUserByUsername((String) principal);
                userId = customUser.getId();
            } catch (UsernameNotFoundException e) {
                throw new AuthException(AuthErrorCode.NOT_EXISTS);
            }
        }

        accessToken = jwtUtils.issueAccessToken((String) principal, userId, authorities);
        refreshToken = jwtUtils.issueRefreshToken((String) principal, userId, authorities);

        // Redis에 토큰 저장
        tokenRepository.save(new Token(userId, refreshToken));
        return new IssuedToken(accessToken, refreshToken);
    }

    public void removeAccessToken(String accessToken){
        // 로그아웃시 유효한 access 토큰을
        // redis에 blacklist로 보내야함
        jwtUtils.validateAccessToken(accessToken);
        invalidTokenRepository.save(new InvalidToken(accessToken));
    }


    // 유효기간 지난 access 토큰 refresh 토큰과 비교해서 재발행
    public IssuedToken reIssueAccessTokenByRefreshToken(String refreshToken) {
        // 미구현
        // EXPIRED_TOKEN

        if (jwtUtils.validateRefreshToken(refreshToken)) {
            Long userId = jwtUtils.getUserIdByRefreshToken(refreshToken);
            String userEmail = jwtUtils.getEmailByRefreshToken(refreshToken);
            List<String> userRole = jwtUtils.getRoleByRefreshToken(refreshToken);

            // 리프레쉬 토큰에 있는 userId로 DB에 저장된 토큰 refresh token 있는지 조회
            Token tokenInDB = tokenRepository.findById(userId).orElseThrow(() -> new JwtException(JwtErrorCode.NOT_EXISTS_TOKEN));
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            // 지금 리프레쉬 토큰하고 DB에 저장된 리프레쉬토큰하고 같다면
            // 새로운 어세스 토큰과 리프레쉬 토큰 재발급
            if (refreshToken.equals(tokenInDB.getRefreshToken())) {
                // 기존 DB의 리프레쉬 토큰 삭제하고
//                IssuedToken newAccessToken = issueToken(userId, userEmail, userRole);
//                log.info("Token reissue : {}", reissuedToken.toString());
//                return reissuedToken;
            } else {
                // 두 리프레쉬 토큰이 다르다면
                // 해당 토큰은 유요하지 않다고 판단하고 따로 저장
                // 이후 예외처리
//                invalidTokenRepository.save(new InvalidToken(tokenInDB.getAccessToken(), tokenInDB.getRefreshToken()));
                throw new JwtException(JwtErrorCode.INVALID_TOKEN);
            }
        }
        throw new JwtException(JwtErrorCode.INVALID_TOKEN);
    }

}
