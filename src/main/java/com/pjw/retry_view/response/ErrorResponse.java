package com.pjw.retry_view.response;

import com.pjw.retry_view.enums.ResponseCode;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ErrorResponse {
    private int code;
    private String message;

    public static ErrorResponse from(ResponseCode code){
        return ErrorResponse.builder()
                .code(code.getHttpStatus().value())
                .message(code.getMessage())
                .build();
    }

    @Builder
    public ErrorResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
