package com.musinsa.subject.controller

import com.musinsa.subject.model.common.Single
import com.musinsa.subject.model.price.LowestPriceByCategoryResponse
import com.musinsa.subject.model.price.LowestTotalPriceBrandWithCategoryDetailsResponse
import com.musinsa.subject.model.price.MinMaxPriceBrandsByCategoryNameResponse
import com.musinsa.subject.service.facade.PriceFacade
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Price API", description = "가격 조회 API")
@Validated
@RestController
@RequestMapping("/prices")
class PriceController(
    private val facade: PriceFacade
) {

    @Operation(
        summary = "카테고리 별 최저가 조회",
        description = "카테고리 별 최저가 브랜드와 상품 가격, 총액을 조회하는 API"
    )
    @GetMapping("/category-lowest-price")
    @ResponseStatus(HttpStatus.OK)
    fun getLowestPriceByCategory(): Single<LowestPriceByCategoryResponse> {
        return Single(facade.getLowestPriceByCategory())
    }

    @Operation(
        summary = "카테고리 별 상품 총액 최저가 브랜드 조회",
        description = "단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회하는 API"
    )
    @GetMapping("/lowest-total-price-brand")
    @ResponseStatus(HttpStatus.OK)
    fun getLowestTotalPriceBrandWithCategoryDetails(): Single<LowestTotalPriceBrandWithCategoryDetailsResponse> {
        return Single(facade.getLowestTotalPriceBrandWithCategoryDetails())
    }

    @Operation(
        summary = "특정 카테고리 최저가 및 최고가 브랜드 조회",
        description = "카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회하는 API"
    )
    @GetMapping("/min-max-price-brand")
    @ResponseStatus(HttpStatus.OK)
    fun getMinMaxPriceBrandsByCategoryName(categoryName: String): Single<MinMaxPriceBrandsByCategoryNameResponse> {
        return Single(facade.getMinMaxPriceBrandsByCategoryName(categoryName))
    }
}
