package com.pjw.retry_view.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.micrometer.common.util.StringUtils;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum SearchType {
    ALL("All"),
    TITLE("Title"),
    TYPE("Type");
    @JsonValue
    private final String code;
    SearchType(String code){ this.code = code; }

    @JsonCreator
    public static SearchType getValue(String code){
        if(StringUtils.isBlank(code)) return SearchType.ALL;
        return Arrays.stream(SearchType.values()).filter((type) -> type.getCode().equals(code)).findFirst().orElse(SearchType.ALL);
    }
}
