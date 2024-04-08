package com.plume.backend.api.auth.domain.vo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthVO {

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

    @Builder
    public AuthVO(long userSeq, long fileSeq, long incomeSeq, String userId, String userPw, String userNickname,
                String userChk, LocalDateTime userBirth, String userEmail, LocalDateTime userDt,
                String privateYn, String userGender, String userIntro, String delYn,
                String followAlarm, String postAlarm, String groupAlarm) {
        this.userSeq = userSeq;
        this.fileSeq = fileSeq;
        this.incomeSeq = incomeSeq;
        this.userId = userId;
        this.userPw = userPw;
        this.userNickname = userNickname;
        this.userChk = userChk;
        this.userBirth = userBirth;
        this.userEmail = userEmail;
        this.userDt = userDt;
        this.privateYn = privateYn;
        this.userGender = userGender;
        this.userIntro = userIntro;
        this.delYn = delYn;
        this.followAlarm = followAlarm;
        this.postAlarm = postAlarm;
        this.groupAlarm = groupAlarm;
    }
}
