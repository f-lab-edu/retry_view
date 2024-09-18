package com.pjw.retry_view.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class LoginRequest implements Serializable {
    @NotEmpty
    private String loginId;
    @NotEmpty
    private String password;
}
