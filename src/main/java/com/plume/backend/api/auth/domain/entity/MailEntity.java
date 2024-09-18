package com.plume.backend.api.auth.domain.entity;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value = "AuthCode", timeToLive = 180)
@Builder
public class MailEntity {

    @Id
    private String code;
}
