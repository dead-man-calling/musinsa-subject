package com.musinsa.subject.model.product;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {

    @Schema(defaultValue = "0")
    @NotNull
    @Positive
    private Long productPrice;
    @Schema(defaultValue = "0")
    @NotNull
    private Long brandId;
    @Schema(defaultValue = "0")
    @NotNull
    private Long categoryId;

}
