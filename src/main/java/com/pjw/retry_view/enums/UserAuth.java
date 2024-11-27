package com.pjw.retry_view.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum UserAuth {
    USER("User"),
    ADMIN("Admin");
    @JsonValue
    private final String code;
    UserAuth(String code){ this.code = code;}

    @JsonCreator
    public static UserAuth getValue(String code){
        return Arrays.stream(UserAuth.values()).filter(auth->auth.getCode().equals(code)).findFirst().orElse(UserAuth.USER);
    }
}
