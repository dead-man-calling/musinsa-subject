package com.musinsa.subject.model.common;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class Success extends Result {

    public Success() {
        super(0, "성공");
    }

    public Success(String message) {
        super(0, message);
    }

}
