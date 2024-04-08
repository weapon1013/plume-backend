package com.plume.backend.api.auth.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "P_USER")
@Builder
@DynamicInsert // DB 에서 not null 필드가 default 값이 있더라도, 해당 어노테이션 선언하지 않으면 오류 발생
public class AuthEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_SEQ")
    private long userSeq;

    @Column(name = "FILE_SEQ")
    private long fileSeq;

    @Column(name = "INCOME_SEQ")
    private long incomeSeq;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "USER_PW")
    private String userPw;

    @Column(name = "USER_NICKNAME")
    private String userNickname;

    @Column(name = "USER_CHK")
    private String userChk;

    @Column(name = "USER_BIRTH")
    private LocalDateTime userBirth;

    @Column(name = "USER_EMAIL")
    private String userEmail;

    @Column(name = "USER_DT")
    private LocalDateTime userDt;

    @Column(name = "PRIVATE_YN")
    private String privateYn;

    @Column(name = "USER_GENDER")
    private String userGender;

    @Column(name = "USER_INTRO")
    private String userIntro;

    @Column(name = "DEL_YN")
    private String delYn;

    @Column(name = "FOLLOW_ALARM")
    private String followAlarm;

    @Column(name = "POST_ALARM")
    private String postAlarm;

    @Column(name = "GROUP_ALARM")
    private String groupAlarm;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.userPw;
    }

    @Override
    public String getUsername() {
        return this.userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
