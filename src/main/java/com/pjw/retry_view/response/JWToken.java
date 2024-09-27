package com.pjw.retry_view.response;

import io.micrometer.common.util.StringUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
public class JWToken {
    private String accessToken;
    private String refreshToken;

    private JWToken(){}
    private JWToken(String accessToken, String refreshToken){
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static JWToken getJWT(String accessToken, String refreshToken){
        return new JWToken(accessToken, refreshToken);
    }
}
