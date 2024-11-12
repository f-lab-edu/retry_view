package com.pjw.retry_view.converter;

import com.pjw.retry_view.dto.MessageType;
import org.springframework.core.convert.converter.Converter;

import java.util.Objects;

public class MessageTypeConverter implements Converter<MessageType, String> {

    @Override
    public String convert(MessageType msgType) {
        if(Objects.isNull(msgType)) return MessageType.CONNECT.getCode();
        return msgType.getCode();
    }
}
