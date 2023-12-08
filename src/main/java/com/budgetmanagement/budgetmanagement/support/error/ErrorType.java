package com.budgetmanagement.budgetmanagement.support.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorType {
    DEFAULT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "오류가 발생했습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "요청사항을 찾지 못했습니다."),
    DUPLICATE_ACCOUNT(HttpStatus.BAD_REQUEST, "이미 존재하는 계정입니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "잘못된 비밀번호입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "잘못된 토큰입니다."),
    EMPTY_HEADER(HttpStatus.BAD_REQUEST, "인증 헤더의 값이 비어있습니다."),
    ;

    private final HttpStatus status;
    private final String message;

    ErrorType(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
