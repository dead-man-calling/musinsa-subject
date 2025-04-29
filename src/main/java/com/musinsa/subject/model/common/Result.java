package com.musinsa.subject.model.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class Result {

    @Schema(defaultValue = "0")
    private final Integer code;
    private final String message;

    public Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
