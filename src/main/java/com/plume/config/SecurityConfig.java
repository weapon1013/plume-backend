package com.plume.config;

import com.plume.backend.api.auth.domain.entity.AuthEntity;
import com.plume.backend.api.auth.repository.jpa.AuthRepository;
import com.plume.backend.filter.JwtFilter;
import com.plume.backend.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class SecurityConfig {

    @Value("${allowed.origin.url}")
    private String originUrls;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private final TokenProvider tokenProvider;

    //    @Bean
//    public WebSecurityCustomizer ignoreFilterChain() {
//        return (web) -> web.ignoring()
//                .requestMatchers("/api/v1/auth/**","/css/**","/js/**","/img/**","/lib/**", "/");
//    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable())
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/api/v1/auth/**").permitAll()
                                .requestMatchers("/api/v1/**").authenticated()
                                .anyRequest().permitAll()
                ).formLogin(form -> form.disable())
                .apply(new JwtSecurityConfigurer(tokenProvider));

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfiguration() {

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.stream(originUrls.split(",")).map(String::trim).collect(Collectors.toList()));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","PATCH"));
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Configuration
    @RequiredArgsConstructor
    public static class JwtSecurityConfigurer implements SecurityConfigurer<DefaultSecurityFilterChain, HttpSecurity> {

        private final TokenProvider tokenProvider;

        @Override
        public void init(HttpSecurity builder) throws Exception {
            // 상태를 유지하지 않는 세션. 인증 정보를 세션에 저장하지 않고, 모든 요청은 서버 측에서 독립적 실행.
            // RESTful API 구현 시 사용.
            builder.sessionManagement(session
                    -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        }

        @Override
        public void configure(HttpSecurity builder) throws Exception {
            builder.addFilterBefore(new JwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);
        }
    }
}
