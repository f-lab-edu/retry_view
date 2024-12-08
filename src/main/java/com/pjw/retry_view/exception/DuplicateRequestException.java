package com.pjw.retry_view.exception;

import org.springframework.http.HttpStatus;

public class DuplicateRequestException extends BusinessException{
    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    public DuplicateRequestException(){}
    public DuplicateRequestException(Exception e) { super(e); }
    public DuplicateRequestException(String msg) { super(msg); }
}
