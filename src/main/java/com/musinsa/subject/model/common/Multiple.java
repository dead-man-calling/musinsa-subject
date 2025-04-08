package com.musinsa.subject.model.common;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@Getter
@EqualsAndHashCode(callSuper = true)
public final class Multiple<T> extends Success {

    private final List<T> data;

    public Multiple(List<T> data) {
        this.data = data;
    }

}
