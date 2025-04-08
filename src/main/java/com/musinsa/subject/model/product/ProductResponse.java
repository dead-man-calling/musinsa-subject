package com.musinsa.subject.model.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponse {

    @Schema(defaultValue = "0")
    private Long productId;
    @Schema(defaultValue = "0")
    private Long productPrice;

}
