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

    private Key key; // 서명 키

    @Value("${server.cookie}")
    private String cookieDomain;

    private AuthRepository authRepository;

//    1. BASE64 디코딩:
//     - secretKey는 일반적으로 환경 변수나 설정 파일에 문자열로 저장됩니다.
//     - Decoders.BASE64.decode(secretKey)는 BASE64로 인코딩된 문자열을 바이너리 데이터(바이트 배열)로 디코딩합니다.
//     - 이 과정은 BASE64로 인코딩된(설정 파일에 저장된 문자열) 키를 원래의 바이너리 형태로 변환합니다.
//
//    2. 키 생성:
//     - Keys.hmacShaKeyFor(keyBytes)는 디코딩된 바이너리 데이터를 사용하여 HMAC SHA256 알고리즘에 사용할 키 객체를 생성합니다.
//     - 이 키는 JWT 토큰의 서명 및 검증에 사용됩니다.

//    <이유 요약> :
//     - 데이터 보안: BASE64로 인코딩된 키는 환경 변수나 설정 파일에 저장할 때 안전한 텍스트 형식으로 유지됩니다.
//     - 키 변환: JWT 서명 및 검증 알고리즘은 바이너리 데이터를 필요로 하기 때문에 BASE64 인코딩된 키를 바이너리 데이터로 변환하여 사용합니다.
//    이 과정을 통해 설정 파일이나 환경 변수에 저장된 문자열 형식의 비밀 키를 안전하게 바이너리 데이터로 변환하여 JWT 서명 및 검증에 사용할 수 있습니다.
    public TokenProvider(@Value("${jwt.secret}") String secretKey, AuthRepository authRepository) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey); // secretKey 를 BASE64로 디코딩
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.authRepository = authRepository;
    }

    public Key getKey() {
        return this.key;
    }

    // 실제 토큰 생성 담당
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

    // 토큰을 생성하고, 쿠키를 발급
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
        //CookieUtils.addCookie("accessTokenExpireIn", String.valueOf(tokenVO.accessTokenExpireTime()), (int)ACCESS_TOKEN_EXPIRE_TIME, cookieDomain, false, false);
        //CookieUtils.addCookie("refreshTokenExpireIn", String.valueOf(tokenVO.refreshTokenExpireTime()), (int)REFRESH_TOKEN_EXPIRE_TIME, cookieDomain, true, false);

        return AuthDTO.TokenResponse.of(tokenVO);
    }

    // Access Token 의 클레임 정보 추출
    public Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (Exception e) {
            return null;
        }
    }

    // JWT 클레임에서 인증 정보 추출하고, Spring Security 의 Authentication 객체 생성
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
