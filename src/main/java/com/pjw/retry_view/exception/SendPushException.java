package com.pjw.retry_view.exception;

import org.springframework.http.HttpStatus;

public class SendPushException extends BusinessException{
    public SendPushException(){}

    public SendPushException(String message){
        super(message);
    }

    public SendPushException(Exception e){
        super(e);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.SERVICE_UNAVAILABLE;
    }
}
