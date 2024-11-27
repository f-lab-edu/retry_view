package com.pjw.retry_view.dto;

import lombok.Getter;

import java.time.Duration;

@Getter
public class RefreshTokenDTO {
    private String key;
    private String refreshToken;
    private Duration duration;

    private RefreshTokenDTO(){}
    private RefreshTokenDTO(String key, String refreshToken, Duration duration){
        this.key = key;
        this.refreshToken = refreshToken;
        this.duration = duration;
    }

    public static RefreshTokenDTO getRefreshToken(String key, String refreshToken, Duration duration){
        return new RefreshTokenDTO(key, refreshToken, duration);
    }
}
