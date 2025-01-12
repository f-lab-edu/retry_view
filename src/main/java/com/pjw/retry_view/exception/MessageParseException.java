package com.pjw.retry_view.exception;

import org.springframework.http.HttpStatus;

public class MessageParseException extends BusinessException {
    public MessageParseException(){}

    public MessageParseException(String message){
        super(message);
    }

    public MessageParseException(Exception e){
        super(e);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}