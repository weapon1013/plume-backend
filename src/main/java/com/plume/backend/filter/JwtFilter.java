package com.plume.backend.filter;

import com.plume.backend.api.auth.domain.entity.AuthEntity;
import com.plume.backend.api.auth.repository.jpa.AuthRepository;
import com.plume.backend.security.TokenProvider;
import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    public final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader(AUTHORIZATION_HEADER);

        if(request.getRequestURI().startsWith("/api/v1/auth")){
            filterChain.doFilter(request, response);
            return;
        }

        if(authorization == null) {
            throw new RuntimeException("Does not have Authorization");
        }

        String accessToken = resolveToken(request);

        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(tokenProvider.getKey()).build().parseClaimsJws(accessToken);
            Authentication authentication = tokenProvider.getAuthentication(claimsJws.getBody(), request);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (SecurityException | MalformedJwtException e) {
            e.printStackTrace();
            //Authorization을 다시 확인해주세요.
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
            //만료된 JWT 토큰 입니다.
        } catch (UnsupportedJwtException e) {
            e.printStackTrace();
            //지원되지 않는 JWT 토큰입니다.
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            //JWT 토큰이 잘못되었습니다.
        }

        filterChain.doFilter(request, response);

        // login api 는 시큐리티 설정 제외됨. 로그인에서 비밀번호 비교 후 성공 시 jwt 발급 및 쿠키 구움. 그리고 이걸 사용자가 갖고 다음 요청부터 인증하고 인증 성공하면 필터에서 시큐리티 컨텍스트에 사용자 정보를 계속 저장
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
