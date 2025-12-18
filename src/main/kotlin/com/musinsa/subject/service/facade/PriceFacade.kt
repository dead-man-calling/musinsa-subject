package com.musinsa.subject.service.facade

import com.musinsa.subject.model.price.LowestPriceByCategoryResponse
import com.musinsa.subject.model.price.LowestTotalPriceBrandWithCategoryDetailsResponse
import com.musinsa.subject.model.price.MinMaxPriceBrandsByCategoryNameResponse

interface PriceFacade {
    fun getLowestPriceByCategory(): LowestPriceByCategoryResponse
    fun getLowestTotalPriceBrandWithCategoryDetails(): LowestTotalPriceBrandWithCategoryDetailsResponse
    fun getMinMaxPriceBrandsByCategoryName(categoryName: String): MinMaxPriceBrandsByCategoryNameResponse
}
