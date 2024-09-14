package com.plume.backend.api.auth.domain.dto;

import com.plume.backend.api.auth.domain.entity.AuthEntity;
import com.plume.backend.api.auth.domain.vo.AuthVO;
import com.plume.backend.api.auth.domain.vo.TokenVO;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthDTO {

    @Getter
    @Builder
    public static class TokenResponse {

        private String accessToken;
        private String refreshToken;
        private long accessTokenExpireTime;
        private long refreshTokenExpireTime;

        public static TokenResponse of(TokenVO tokenVO) {
            return TokenResponse.builder()
                    .accessToken(tokenVO.accessToken())
                    .refreshToken(tokenVO.refreshToken())
                    .accessTokenExpireTime(tokenVO.accessTokenExpireTime())
                    .refreshTokenExpireTime(tokenVO.refreshTokenExpireTime())
                    .build();
        }
    }

    @Getter
    @Setter
    public static class LoginRequest {

        private String userId;
        private String userPw;

        public AuthVO toVO() {
            return AuthVO.builder()
                    .userId(userId)
                    .userPw(userPw)
                    .build();
        }
    }

    @Getter
    @Setter
    public static class SignRequest {
        private long userSeq;
        private long fileSeq;
        private long incomeSeq;
        private String userId;
        private String userPw;
        private String userNickname;
        private String userChk;
        private LocalDateTime userBirth;
        private String userEmail;
        private LocalDateTime userDt;
        private String privateYn;
        private String userGender;
        private String userIntro;
        private String delYn;
        private String followAlarm;
        private String postAlarm;
        private String groupAlarm;

        public AuthEntity toEntity() {
            return AuthEntity.builder()
                    .incomeSeq(incomeSeq)
                    .userId(userId)
                    .userNickname(userNickname)
                    .userChk(userChk)
                    .userEmail(userEmail)
                    .privateYn(privateYn)
                    .delYn("N")
                    .userGender(userGender)
                    .userBirth(userBirth)
                    .build();
        }
    }


}
