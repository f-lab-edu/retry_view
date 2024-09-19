package com.pjw.retry_view.exception;

import java.util.function.Supplier;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(){}

    public UserNotFoundException(String msg){
        super(msg);
    }

    public UserNotFoundException(Exception e){
        super(e);
    }
}
