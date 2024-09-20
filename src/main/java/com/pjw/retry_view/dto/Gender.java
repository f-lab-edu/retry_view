package com.pjw.retry_view.dto;

import lombok.Getter;

@Getter
public enum Gender {
    MALE(0),
    FEMALE(1);

    private final int code;

    Gender(int code){
        this.code = code;
    }

}
