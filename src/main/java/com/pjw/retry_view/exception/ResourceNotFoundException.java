package com.pjw.retry_view.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends BusinessException {
    public ResourceNotFoundException(){}

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(Exception e){
        super(e);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
