package com.pjw.retry_view.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.micrometer.common.util.StringUtils;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ImageType {
    BOARD("Board"),
    NOTICE("Notice"),
    EVENT("Event");
    @JsonValue
    private String code;
    ImageType(String code){
        this.code = code;
    }

    @JsonCreator
    public static ImageType getValue(String code){
        if(StringUtils.isBlank(code)) return ImageType.BOARD;
        return Arrays.stream(ImageType.values()).filter((type)->type.getCode().equals(code)).findFirst().orElse(ImageType.BOARD);
    }
}
