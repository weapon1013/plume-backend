package com.plume.backend.api.auth.service;

import com.plume.backend.api.auth.domain.dto.AuthDTO;
import com.plume.backend.api.auth.domain.entity.AuthEntity;
import com.plume.backend.api.auth.domain.vo.AuthVO;
import com.plume.backend.api.auth.repository.jpa.AuthRepository;
import com.plume.backend.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final AuthRepository authRepository;

    private final PasswordEncoder passwordEncoder;

    private final TokenProvider tokenProvider;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<AuthEntity> authEntity = authRepository.findByUserIdAndDelYn(username, "N");
        authEntity.orElseThrow(() -> new UsernameNotFoundException("Not Found User : " + username));

        return authEntity.get();
    }

    public AuthDTO.TokenResponse login(AuthVO user) {

        Optional<AuthEntity> authEntity = authRepository.findByUserIdAndDelYn(user.getUserId(), "N");
        authEntity.orElseThrow(() -> new UsernameNotFoundException("Not Found User : " + user.getUserId()));

        if(!passwordEncoder.matches(user.getUserPw(), authEntity.get().getPassword())) {
            throw new RuntimeException("Match Fail");
        }

        return tokenProvider.generateToken(authEntity.get());
    }

    public void join(AuthEntity authEntity) {
        authEntity.setUserPw(passwordEncoder.encode("test1"));
        authRepository.save(authEntity);
    }

    public String t(long userSeq) {
        var authEntity = authRepository.findByUserSeq(userSeq);
        return authEntity.get().getUserNickname();
    }
}
