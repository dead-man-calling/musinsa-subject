package com.musinsa.subject.model.product;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
