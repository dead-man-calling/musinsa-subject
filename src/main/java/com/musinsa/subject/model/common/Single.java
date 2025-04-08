package com.musinsa.subject.model.common;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = true)
public final class Single<T> extends Success {

    private final T data;

    public Single(T data) {
        this.data = data;
    }

}
