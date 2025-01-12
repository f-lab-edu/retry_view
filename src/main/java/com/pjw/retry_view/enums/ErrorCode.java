package com.pjw.retry_view.enums;

import org.springframework.http.HttpStatus;

public enum ErrorCode implements ResponseCode {
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "데이터를 찾을 수 없습니다."),
    CHAT_MESSAGE_PARSE(HttpStatus.BAD_REQUEST, "채팅 메시지 변환에 실패했습니다."),
    DUPLICATE_REQ(HttpStatus.BAD_REQUEST, "중복된 요청입니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    NOT_MY_RESOURCE(HttpStatus.FORBIDDEN, "접근 불가능한 데이터입니다."),
    USER_LOGIN_FAILED(HttpStatus.NOT_FOUND, "로그인이 실패하였습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다.");
    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message){
        this.httpStatus = httpStatus;
        this.message = message;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
