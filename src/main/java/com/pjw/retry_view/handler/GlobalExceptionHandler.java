package com.pjw.retry_view.handler;

import com.pjw.retry_view.exception.BusinessException;
import com.pjw.retry_view.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// 전역 ExceptionHandler 설정
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ErrorResponse> handle(BusinessException be){
        ErrorResponse er = com.pjw.retry_view.response.ErrorResponse.builder()
                .code(be.getHttpStatus().value())
                .message(be.getMessage()).build();
        return ResponseEntity.status(be.getHttpStatus()).body(er);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> defaultHandle2(MethodArgumentNotValidException ex){
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        String message = ex.getAllErrors().get(0).getDefaultMessage();
        ErrorResponse er = com.pjw.retry_view.response.ErrorResponse.builder()
                .code(httpStatus.value())
                .message(message).build();
        return ResponseEntity.status(httpStatus).body(er);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> defaultHandle(Exception ex){
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponse er = com.pjw.retry_view.response.ErrorResponse.builder()
                .code(httpStatus.value())
                .message(ex.getMessage()).build();
        return ResponseEntity.status(httpStatus).body(er);
    }
}
