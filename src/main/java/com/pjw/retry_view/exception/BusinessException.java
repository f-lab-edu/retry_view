package com.pjw.retry_view.exception;

import org.springframework.http.HttpStatus;

public abstract class BusinessException extends  RuntimeException {
    public abstract HttpStatus getHttpStatus();

    public BusinessException() {}
    public BusinessException(String message) {
        super(message);
    }
    public BusinessException(Exception e){
        super(e);
    }
}
