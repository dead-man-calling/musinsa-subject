package com.musinsa.subject.model.price;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class LowestPriceByCategoryResponse {

    public record Category(String categoryName, String brandName, String productPrice) {}

    private List<Category> categoryLowestPrices;
    private String totalPrice;

}
