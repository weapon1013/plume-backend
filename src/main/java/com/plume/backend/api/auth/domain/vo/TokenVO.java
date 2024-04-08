package com.plume.backend.api.auth.domain.vo;

public record TokenVO(
        String accessToken,
        String refreshToken,
        long accessTokenExpireTime,
        long refreshTokenExpireTime
) {}
