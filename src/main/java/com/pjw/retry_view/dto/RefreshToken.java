package com.pjw.retry_view.dto;

import lombok.Getter;

import java.time.Duration;

@Getter
public class RefreshToken {
    private String key;
    private String refreshToken;
    private Duration duration;

    private RefreshToken(){}
    private RefreshToken(String key, String refreshToken, Duration duration){
        this.key = key;
        this.refreshToken = refreshToken;
        this.duration = duration;
    }

    public static RefreshToken getRefreshToken(String key, String refreshToken, Duration duration){
        return new RefreshToken(key, refreshToken, duration);
    }
}
