package com.musinsa.subject.model.category;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryResponse {

    @Schema(defaultValue = "0")
    private Long categoryId;
    private String categoryName;

}
