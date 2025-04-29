package com.musinsa.subject.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Getter
@RequiredArgsConstructor
public enum InternalExceptionType {

    DEFAULT(-1, "핸들링 대상이 아닌 예외가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR, LogLevel.ERROR, Exception.class),
    METHOD_ARGUMENT_NOT_VALID(-1000, "유효하지 않은 변수가 존재합니다.",HttpStatus.BAD_REQUEST, LogLevel.ERROR, MethodArgumentNotValidException.class),
    CONSTRAINT_VIOLATION(-1001,"유효하지 않은 변수가 존재합니다.", HttpStatus.BAD_REQUEST, LogLevel.ERROR, ConstraintViolationException.class);

    private final Integer code;
    private final String message;
    private final HttpStatus httpStatus;
    private final LogLevel logLevel;
    private final Class<? extends Exception> exceptionClass;

}
