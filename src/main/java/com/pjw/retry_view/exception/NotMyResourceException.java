package com.pjw.retry_view.exception;

import org.springframework.http.HttpStatus;

public class NotMyResourceException extends BusinessException{
    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.FORBIDDEN;
    }

    public NotMyResourceException(){}
    public NotMyResourceException(String msg){
        super(msg);
    }
    public NotMyResourceException(Exception e){
        super(e);
    }
}
