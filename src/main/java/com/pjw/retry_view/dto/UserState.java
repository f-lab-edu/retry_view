package com.pjw.retry_view.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum UserState{
    NORMAL(1),
    WITHDRAW(9);
    @JsonValue
    private final int code;
    UserState(int code){
        this.code = code;
    }

    @JsonCreator
    public static UserState getValue(int code) {
        return Arrays.stream(UserState.values()).filter(state -> state.getCode() == code).findFirst().orElse(UserState.NORMAL);
    }
}
