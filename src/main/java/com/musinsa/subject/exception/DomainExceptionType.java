package com.musinsa.subject.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum DomainExceptionType {

    DEFAULT(-1, "핸들링 대상이 아닌 예외가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR, LogLevel.ERROR);

    private final int code;
    private final String message;
    private final HttpStatus httpStatus;
    private final LogLevel logLevel;

}
