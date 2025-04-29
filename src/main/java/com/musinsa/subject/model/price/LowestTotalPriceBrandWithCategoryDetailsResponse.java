package com.musinsa.subject.model.price;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class LowestTotalPriceBrandWithCategoryDetailsResponse {

    public record CategoryDetail(String categoryName, String productPrice) {
    }

    @Getter
    @Setter
    @Builder
    public static class LowestPrice {

        private String brandName;
        private List<CategoryDetail> categories;
        private String totalPrice;

    }

    public LowestPrice lowestPrice;

}
