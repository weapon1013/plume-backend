package com.plume.backend.api.auth.service;

import com.plume.backend.api.auth.domain.dto.AuthDTO;
import com.plume.backend.api.auth.domain.entity.AuthEntity;
import com.plume.backend.api.auth.domain.entity.MailEntity;
import com.plume.backend.api.auth.domain.vo.AuthVO;
import com.plume.backend.api.auth.domain.vo.MailVO;
import com.plume.backend.api.auth.repository.jpa.AuthRepository;
import com.plume.backend.api.auth.repository.jpa.MailRepository;
import com.plume.backend.security.TokenProvider;
import com.plume.common.util.MailUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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

    private final MailRepository mailRepository;

    private final RedisTemplate<String,String> redisTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<AuthEntity> authEntity = authRepository.findByUserIdAndDelYn(username, "N");
        authEntity.orElseThrow(() -> new UsernameNotFoundException("Not Found User : " + username));

        return authEntity.get();
    }

    public void sign(AuthEntity authEntity) {
        authEntity.setUserPw(passwordEncoder.encode("test1"));
        authRepository.save(authEntity);
    }

    public AuthDTO.TokenResponse login(AuthVO user) {

        Optional<AuthEntity> authEntity = authRepository.findByUserIdAndDelYn(user.getUserId(), "N");
        authEntity.orElseThrow(() -> new UsernameNotFoundException("Not Found User : " + user.getUserId()));

        if(!passwordEncoder.matches(user.getUserPw(), authEntity.get().getPassword())) {
            throw new RuntimeException("Match Fail");
        }

        return tokenProvider.generateToken(authEntity.get());
    }

    public AuthDTO.CheckResponse idOrNicknameCheck(String checkStr, String type) {

        boolean check = true;

        if(type.equals("id")) {
            check = authRepository.existsByUserIdAndDelYn(checkStr, "N");
        }else {
            check = authRepository.existsByUserNicknameAndDelYn(checkStr, "N");
        }

        return AuthDTO.CheckResponse.of(check);
    }

    public boolean emailCheck(MailVO mail) throws Exception {

        boolean check = authRepository.existsByUserEmailAndDelYn(mail.getUserEmail(), "N");

        if(!check) {

            String authCode = RandomStringUtils.randomNumeric(6);

            MailUtils.sendMail(mail.getUserEmail(), authCode);
            mailRepository.save(MailEntity.builder().code(authCode).build());
        }

        return check;
    }

    public boolean authCodeCheck(String code) {

        Optional<MailEntity> auth = mailRepository.findById(code);
        boolean check = auth.isPresent();

        if(check) {
            mailRepository.deleteById(code);
        }

        return !check;
    }

}
