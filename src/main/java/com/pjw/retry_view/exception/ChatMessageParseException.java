package com.pjw.retry_view.exception;

import org.springframework.http.HttpStatus;

public class ChatMessageParseException extends BusinessException {
    public ChatMessageParseException(){}

    public ChatMessageParseException(String message){
        super(message);
    }

    public ChatMessageParseException(Exception e){
        super(e);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
