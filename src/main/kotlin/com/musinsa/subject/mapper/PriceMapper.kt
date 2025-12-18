package com.musinsa.subject.mapper

import com.musinsa.subject.entity.Product
import com.musinsa.subject.model.price.LowestPriceByCategoryResponse
import com.musinsa.subject.model.price.LowestTotalPriceBrandWithCategoryDetailsResponse
import com.musinsa.subject.model.price.MinMaxPriceBrandsByCategoryNameResponse
import io.swagger.v3.oas.annotations.Hidden
import org.mapstruct.*

@Hidden
@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
interface PriceMapper {

    fun toResponseCategories(source: List<Product>): List<LowestPriceByCategoryResponse.Category>

    @Mapping(source = "category.categoryName", target = "categoryName")
    @Mapping(source = "brand.brandName", target = "brandName")
    @Mapping(source = "productPrice", target = "productPrice", qualifiedByName = ["longToString"])
    fun toResponseCategory(source: Product): LowestPriceByCategoryResponse.Category

    fun toResponseCategoryDetails(source: List<Product>): List<LowestTotalPriceBrandWithCategoryDetailsResponse.CategoryDetail>

    @Mapping(source = "category.categoryName", target = "categoryName")
    @Mapping(source = "productPrice", target = "productPrice", qualifiedByName = ["longToString"])
    fun toResponseCategoryDetail(source: Product): LowestTotalPriceBrandWithCategoryDetailsResponse.CategoryDetail

    fun toResponseBrands(source: List<Product>): List<MinMaxPriceBrandsByCategoryNameResponse.Brand>

    @Mapping(source = "brand.brandName", target = "brandName")
    @Mapping(source = "productPrice", target = "productPrice", qualifiedByName = ["longToString"])
    fun toResponseBrand(source: Product): MinMaxPriceBrandsByCategoryNameResponse.Brand

    @Named("longToString")
    fun longToString(value: Long?): String? {
        return value?.let { String.format("%,d", it) }
    }
}
