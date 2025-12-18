package com.musinsa.subject.exception

import jakarta.validation.ConstraintViolationException
import org.springframework.boot.logging.LogLevel
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import kotlin.reflect.KClass

enum class InternalExceptionType(
    val code: Int,
    val message: String,
    val httpStatus: HttpStatus,
    val logLevel: LogLevel,
    val exceptionClass: KClass<out Exception>
) {

    DEFAULT(-1, "핸들링 대상이 아닌 예외가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR, LogLevel.ERROR, Exception::class),
    METHOD_ARGUMENT_NOT_VALID(-1000, "유효하지 않은 변수가 존재합니다.", HttpStatus.BAD_REQUEST, LogLevel.ERROR, MethodArgumentNotValidException::class),
    CONSTRAINT_VIOLATION(-1001, "유효하지 않은 변수가 존재합니다.", HttpStatus.BAD_REQUEST, LogLevel.ERROR, ConstraintViolationException::class)

}
