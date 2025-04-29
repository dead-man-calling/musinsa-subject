package com.musinsa.subject.model.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = true)
public final class Fail extends Result {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String reason;

    public Fail(String message) {
        super(-1, message);
    }

    public Fail(String message, String reason) {
        super(-1, message);
        this.reason = reason;
    }

    public Fail(int code, String message, String reason) {
        super(code, message);
        this.reason = reason;
    }

}
