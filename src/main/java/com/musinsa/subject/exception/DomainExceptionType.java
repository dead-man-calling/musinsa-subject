package com.musinsa.subject.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum DomainExceptionType {

    DEFAULT(-1, "핸들링 대상이 아닌 예외가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR, LogLevel.ERROR),
    BRAND_NOT_FOUND(-1000, "브랜드 정보를 찾지 못했습니다.", HttpStatus.INTERNAL_SERVER_ERROR, LogLevel.ERROR),
    CATEGORY_NOT_FOUND(-1001, "카테고리 정보를 찾지 못했습니다.", HttpStatus.INTERNAL_SERVER_ERROR, LogLevel.ERROR),
    PRODUCT_NOT_FOUND(-1002, "상품 정보를 찾지 못했습니다.", HttpStatus.INTERNAL_SERVER_ERROR, LogLevel.ERROR);

    private final int code;
    private final String message;
    private final HttpStatus httpStatus;
    private final LogLevel logLevel;

}
