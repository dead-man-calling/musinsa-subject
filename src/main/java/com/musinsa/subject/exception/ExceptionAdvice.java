package com.musinsa.subject.exception;

import com.musinsa.subject.model.common.Fail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionAdvice {

    @ExceptionHandler(DomainException.class)
    protected ResponseEntity<Fail> domainException(DomainException e) {
        log.error(e.getMessage(), e);

        return ResponseEntity.status(e.getType().getHttpStatus())
                .body(new Fail(e.getType().getCode(), e.getType().getMessage(), e.getReason()));
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Fail> internalException(Exception e) {
        var i = new InternalException(e);

        log.error(i.getMessage(), i);

        return ResponseEntity.status(i.getType().getHttpStatus())
                .body(new Fail(i.getType().getCode(), i.getType().getMessage(), i.getException().getMessage()));
    }

}
