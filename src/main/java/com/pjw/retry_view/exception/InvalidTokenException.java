package com.pjw.retry_view.exception;

import org.springframework.http.HttpStatus;

public class InvalidTokenException extends BusinessException{
    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.UNAUTHORIZED;
    }

    public InvalidTokenException(){}
    public InvalidTokenException(String msg){
        super(msg);
    }
    public InvalidTokenException(Exception e){
        super(e);
    }
}
