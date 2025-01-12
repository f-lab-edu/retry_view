package com.pjw.retry_view.request;

import com.pjw.retry_view.enums.MessageType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageRequest {
    private Long chatId;
    private MessageType type;
    private String message;
    private Long sender;

    @Override
    public String toString() {
        return "MessageRequest{" +
                "chatId=" + chatId +
                ", type='" + type + '\'' +
                ", message='" + message + '\'' +
                ", sender=" + sender +
                '}';
    }
}
