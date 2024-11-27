package com.pjw.retry_view.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.micrometer.common.util.StringUtils;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum CategoryType {
    MAIN("Main"),
    Sub("Sub");
    @JsonValue
    private String code;
    CategoryType(String code){ this.code = code;}

    @JsonCreator
    public static CategoryType getValue(String code){
        if(StringUtils.isBlank(code)) return CategoryType.MAIN;
        return Arrays.stream(CategoryType.values()).filter((type)->type.getCode().equals(code)).findFirst().orElse(CategoryType.MAIN);
    }
}
