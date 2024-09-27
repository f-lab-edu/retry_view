package com.pjw.retry_view.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private String msg;
    private JWToken token;
}
