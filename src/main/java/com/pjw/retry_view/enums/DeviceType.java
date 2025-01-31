package com.pjw.retry_view.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.micrometer.common.util.StringUtils;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum DeviceType {
    WEB("Web"),
    AOS("Ios"),
    IOS("Aos"),
    OTHER("Other");
    @JsonValue
    private final String code;
    DeviceType(String code){ this.code = code; }

    @JsonCreator
    public static DeviceType getValue(String code){
        if(StringUtils.isBlank(code)) return DeviceType.OTHER;
        return Arrays.stream(DeviceType.values()).filter(type -> type.getCode().equals(code)).findFirst().orElse(DeviceType.OTHER);
    }
}
