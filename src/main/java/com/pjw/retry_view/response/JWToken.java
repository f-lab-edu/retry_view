package com.pjw.retry_view.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JWToken {
    private String accessToken;
    private String refreshToken;
}
