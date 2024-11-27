package com.pjw.retry_view.exception;

import org.springframework.http.HttpStatus;

public class UserLoginFailedException extends BusinessException{
    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }

    public UserLoginFailedException(){
        super();
    }

    public UserLoginFailedException(String msg){
        super(msg);
    }

    public UserLoginFailedException(Exception e){
        super(e);
    }
}
