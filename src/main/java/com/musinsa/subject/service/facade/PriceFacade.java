package com.musinsa.subject.service.facade;

import com.musinsa.subject.model.price.LowestPriceByCategoryResponse;
import com.musinsa.subject.model.price.LowestTotalPriceBrandWithCategoryDetailsResponse;
import com.musinsa.subject.model.price.MinMaxPriceBrandsByCategoryNameResponse;

public interface PriceFacade {

    LowestPriceByCategoryResponse getLowestPriceByCategory();

    LowestTotalPriceBrandWithCategoryDetailsResponse getLowestTotalPriceBrandWithCategoryDetails();

    MinMaxPriceBrandsByCategoryNameResponse getMinMaxPriceBrandsByCategoryName(String categoryName);

}
