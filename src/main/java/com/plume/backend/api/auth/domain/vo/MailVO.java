package com.plume.backend.api.auth.domain.vo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MailVO {

    private String userEmail;

    @Builder
    public MailVO(String userEmail) {
        this.userEmail = userEmail;
    }
}
