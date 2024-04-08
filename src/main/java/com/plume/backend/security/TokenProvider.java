package com.plume.backend.security;

import com.plume.backend.api.auth.domain.dto.AuthDTO;
import com.plume.backend.api.auth.domain.entity.AuthEntity;
import com.plume.backend.api.auth.domain.vo.TokenVO;
import com.plume.backend.api.auth.repository.jpa.AuthRepository;
import com.plume.common.util.CookieUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Optional;

@Component
public class TokenProvider {

    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;            // 30분
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;  // 7일

    private Key key;

    @Value("${server.cookie}")
    private String cookieDomain;

    private AuthRepository authRepository;

    public TokenProvider(@Value("${jwt.secret}") String secretKey, AuthRepository authRepository) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.authRepository = authRepository;
    }

    public Key getKey() {
        return this.key;
    }

    public String generateToken(AuthEntity auth, String tokenType) {

        Date tokenExpireIn = new Date(new Date().getTime()
                + (tokenType.equals("access") ? ACCESS_TOKEN_EXPIRE_TIME : REFRESH_TOKEN_EXPIRE_TIME));

        // Header, Payload, Signature
        return Jwts.builder()
                .setHeaderParam("typ","JWT")
                .signWith(key, SignatureAlgorithm.HS512)
                .setSubject(String.valueOf(auth.getUserSeq()))
                .claim("userId", auth.getUserId())
                .claim("userNickname", auth.getUserNickname())
                .setExpiration(tokenExpireIn)
                .compact();
    }

    public AuthDTO.TokenResponse generateToken(AuthEntity auth) {

        String accessToken = generateToken(auth, "access");
        String refreshToken = generateToken(auth, "refresh");

        Date accessTokenExpire = new Date(new Date().getTime() + ACCESS_TOKEN_EXPIRE_TIME);
        Date refreshTokenExpire = new Date(new Date().getTime() + REFRESH_TOKEN_EXPIRE_TIME);

        var tokenVO = new TokenVO(
                accessToken,
                refreshToken,
                accessTokenExpire.getTime() / 1000,
                refreshTokenExpire.getTime() / 1000
        );

        CookieUtils.addCookie("accessToken", accessToken, (int)ACCESS_TOKEN_EXPIRE_TIME, cookieDomain, false, false);
        CookieUtils.addCookie("refreshToken", refreshToken, (int)REFRESH_TOKEN_EXPIRE_TIME, cookieDomain, true, false);
        CookieUtils.addCookie("accessTokenExpireIn", String.valueOf(tokenVO.accessTokenExpireTime()), (int)ACCESS_TOKEN_EXPIRE_TIME, cookieDomain, false, false);
        CookieUtils.addCookie("refreshTokenExpireIn", String.valueOf(tokenVO.refreshTokenExpireTime()), (int)REFRESH_TOKEN_EXPIRE_TIME, cookieDomain, true, false);

        return AuthDTO.TokenResponse.of(tokenVO);
    }

    public Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (Exception e) {
            return null;
        }
    }

    public Authentication getAuthentication(Claims claims, HttpServletRequest request) {

        if(null == claims) {}

        Optional<AuthEntity> optionalAuthEntity = authRepository.findByUserIdAndDelYn(String.valueOf(claims.get("userId")), "N");
        AuthEntity user = optionalAuthEntity.get();

        AuthEntity auth = AuthEntity.builder()
                .userSeq(user.getUserSeq())
                .userId(user.getUserId())
                .userNickname(user.getUserNickname())
                .incomeSeq(user.getIncomeSeq())
                .userEmail(user.getUserEmail())
                .build();

        return new UsernamePasswordAuthenticationToken(auth, "");
    }

}
