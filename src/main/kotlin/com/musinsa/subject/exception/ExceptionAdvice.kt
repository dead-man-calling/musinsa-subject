package com.musinsa.subject.exception

import com.musinsa.subject.model.common.Fail
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionAdvice {

    companion object {
        private val log = LoggerFactory.getLogger(ExceptionAdvice::class.java)
    }

    @ExceptionHandler(DomainException::class)
    private fun domainException(e: DomainException): ResponseEntity<Fail> {
        log.error(e.message, e)

        return ResponseEntity.status(e.type.httpStatus)
            .body(Fail(e.type.code, e.type.message, e.reason))
    }

    @ExceptionHandler(Exception::class)
    private fun internalException(e: Exception): ResponseEntity<Fail> {
        val i = InternalException(e)

        log.error(i.message, i)

        return ResponseEntity.status(i.type.httpStatus)
            .body(Fail(i.type.code, i.type.message, i.exception.message))
    }

}
