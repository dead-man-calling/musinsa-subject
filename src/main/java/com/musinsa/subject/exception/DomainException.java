package com.musinsa.subject.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DomainException extends RuntimeException {

    private DomainExceptionType type;
    private Object data;
    private String reason;

    public DomainException(DomainExceptionType type) {
        this.type = type;
    }

}
