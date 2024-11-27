package com.pjw.retry_view.handler;

import com.pjw.retry_view.exception.BusinessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// 전역 ExceptionHandler 설정
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<?> handle(BusinessException be){
        final ErrorResponse errorResponse = ErrorResponse.builder(be, be.getHttpStatus(), be.getMessage()).build();
        return ResponseEntity.status(be.getHttpStatus()).body(errorResponse);
    }
}
