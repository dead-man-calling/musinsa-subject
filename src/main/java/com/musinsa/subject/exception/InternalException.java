package com.musinsa.subject.exception;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
public class InternalException extends RuntimeException {

    private final Exception exception;
    private final InternalExceptionType type;

    public InternalException(Exception e) {
        this.exception = e;

        Optional<InternalExceptionType> result =
                Arrays.stream(InternalExceptionType.values())
                        .filter(k -> k.getExceptionClass() == e.getClass())
                        .findAny();

        this.type = result.orElse(InternalExceptionType.DEFAULT);
    }

}
