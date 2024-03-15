package com.ssafy.fullerting.security.service;

import com.ssafy.fullerting.security.exception.JwtErrorCode;
import com.ssafy.fullerting.security.exception.JwtException;
import com.ssafy.fullerting.security.model.dto.response.IssuedToken;
import com.ssafy.fullerting.security.model.entity.InvalidToken;
import com.ssafy.fullerting.security.model.entity.Token;
import com.ssafy.fullerting.security.repository.InvalidTokenRepository;
import com.ssafy.fullerting.security.repository.TokenRepository;
import com.ssafy.fullerting.security.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtUtils jwtUtils;
    private final TokenRepository tokenRepository;
    private final InvalidTokenRepository invalidTokenRepository;
    public IssuedToken issueToken(Authentication authentication) {

        Object principal = authentication.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        String accessToken = jwtUtils.issueAccessToken((String)principal, authorities);
        String refreshToken = jwtUtils.issueRefreshToken((String) principal, authorities);

        // Redis에 토큰 저장
        tokenRepository.save(new Token(id, accessToken, refreshToken));
        return new IssuedToken(accessToken, refreshToken);
    }

    public void removeToken(Long id){
        Token token = tokenRepository.findById(id).orElseThrow(() -> new JwtException(JwtErrorCode.NOT_EXISTS_TOKEN));
        tokenRepository.delete(token);
    }


    // 유효기간 지난 access 토큰 refresh 토큰과 비교해서 재발행
    public IssuedToken reIssueToken(String refreshToken) {
        if (jwtUtils.validateRefreshToken(refreshToken)) {
            Long userId = jwtUtils.getUserIdByRefreshToken(refreshToken);
            String userEmail = jwtUtils.getEmailByRefreshToken(refreshToken);
            String userRole = jwtUtils.getRoleByRefreshToken(refreshToken);

            // 리프레쉬 토큰에 있는 userId로 DB에 저장된 토큰 조회
            Token tokenInDB = tokenRepository.findById(userId).orElseThrow(() -> new JwtException(JwtErrorCode.NOT_EXISTS_TOKEN));

            // 지금 리프레쉬 토큰하고 DB에 저장된 리프레쉬토큰하고 같다면
            // 기존 DB의 어세스 토큰, 리프레쉬 토큰 삭제하고
            // 새로운 어세스 토큰과 리프레쉬 토큰 재발급
            if (refreshToken.equals(tokenInDB.getRefreshToken())) {
                removeToken(userId);
                IssuedToken reissuedToken = issueToken(userId, userEmail, userRole);
                log.info("Token reissue : {}", reissuedToken.toString());
                return reissuedToken;
            } else {
                // 두 리프레쉬 토큰이 다르다면
                // 해당 토큰은 유요하지 않다고 판단하고 따로 저장
                // 이후 예외처리
                removeToken(userId);
                invalidTokenRepository.save(new InvalidToken(tokenInDB.getAccessToken(), tokenInDB.getRefreshToken()));
                throw new JwtException(JwtErrorCode.INVALID_TOKEN);
            }
        }
        throw new JwtException(JwtErrorCode.INVALID_TOKEN);
    }

}
