package com.musinsa.subject.model.price;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class MinMaxPriceBrandsByCategoryNameResponse {

    public record Brand(String brandName, String productPrice) {}

    private String categoryName;
    private List<Brand> minPriceBrands;
    private List<Brand> maxPriceBrands;

}
