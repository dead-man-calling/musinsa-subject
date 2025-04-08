package com.musinsa.subject.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum InternalExceptionType {

    DEFAULT(-1, "핸들링 대상이 아닌 예외가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR, LogLevel.ERROR, Exception.class);

    private final Integer code;
    private final String message;
    private final HttpStatus httpStatus;
    private final LogLevel logLevel;
    private final Class<? extends Exception> exceptionClass;

}
