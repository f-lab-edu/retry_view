package com.pjw.retry_view.dto;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum UserState{
    NORMAL(1),
    WITHDRAW(9);
    private final int code;
    UserState(int code){
        this.code = code;
    }

    public static UserState getValue(int code) {
        return Arrays.stream(UserState.values()).filter(state -> state.getCode() == code).findFirst().orElse(null);
    }
}
