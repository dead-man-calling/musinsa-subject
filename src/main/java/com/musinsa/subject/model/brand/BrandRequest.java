package com.musinsa.subject.model.brand;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrandRequest {

    @NotNull
    private String brandName;

}
