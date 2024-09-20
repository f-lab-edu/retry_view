package com.pjw.retry_view.exception;


public class UserNotFoundException extends ResourceNotFoundException {
    public UserNotFoundException(){
        super();
    }

    public UserNotFoundException(String msg){
        super(msg);
    }

    public UserNotFoundException(Exception e){
        super(e);
    }
}
