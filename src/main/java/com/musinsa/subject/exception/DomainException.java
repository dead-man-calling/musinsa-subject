package com.musinsa.subject.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DomainException extends RuntimeException {

    private DomainExceptionType type;
    private Object data;
    private String reason;

}
