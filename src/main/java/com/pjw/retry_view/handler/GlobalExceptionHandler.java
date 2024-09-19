package com.pjw.retry_view.handler;

import com.pjw.retry_view.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// 전역 ExceptionHandler 설정
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<?> handle(UserNotFoundException me){
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        final ErrorResponse errorResponse = ErrorResponse.builder(me, httpStatus, "Login Failed : User Not Found.").build();
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }
}
