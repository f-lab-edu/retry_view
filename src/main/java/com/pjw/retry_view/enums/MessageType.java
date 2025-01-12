package com.pjw.retry_view.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.micrometer.common.util.StringUtils;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum MessageType {
    CONNECT("Connect"),
    DISCONNECT("Disconnect"),
    ENTER("Enter"),
    MESSAGE("Message"),
    PING("Ping"),
    EXIT("Exit");
    @JsonValue
    private String code;
    MessageType(String code){this.code = code;}

    @JsonCreator
    public static MessageType getValue(String code){
        if(StringUtils.isBlank(code)) return MessageType.CONNECT;
        return Arrays.stream(MessageType.values()).filter(type -> type.getCode().equals(code)).findFirst().orElse(MessageType.CONNECT);
    }
}
