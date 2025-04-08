package com.musinsa.subject.model.brand;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrandResponse {

    @Schema(defaultValue = "0")
    private Long brandId;
    private String brandName;

}
