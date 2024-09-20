package com.pjw.retry_view.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.ObjectError;

import java.util.List;

@Getter
@Setter
public class RegistUserResponse {
    private String name;
    private String loginId;
    private String nickname;

    private List<ObjectError> bindingErrors;
}
