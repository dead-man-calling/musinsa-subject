package com.musinsa.subject.exception

import org.springframework.boot.logging.LogLevel
import org.springframework.http.HttpStatus

enum class DomainExceptionType(
    val code: Int,
    val message: String,
    val httpStatus: HttpStatus,
    val logLevel: LogLevel
) {
    DEFAULT(-1, "핸들링 대상이 아닌 예외가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR, LogLevel.ERROR),
    BRAND_NOT_FOUND(-1000, "브랜드 정보를 찾지 못했습니다.", HttpStatus.NOT_FOUND, LogLevel.WARN),
    CATEGORY_NOT_FOUND(-1001, "카테고리 정보를 찾지 못했습니다.", HttpStatus.NOT_FOUND, LogLevel.WARN),
    PRODUCT_NOT_FOUND(-1002, "상품 정보를 찾지 못했습니다.", HttpStatus.NOT_FOUND, LogLevel.WARN)
}
