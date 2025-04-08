package com.musinsa.subject.mapper;

import com.musinsa.subject.entity.Product;
import com.musinsa.subject.model.price.LowestPriceByCategoryResponse;
import com.musinsa.subject.model.price.LowestTotalPriceBrandWithCategoryDetailsResponse;
import com.musinsa.subject.model.price.MinMaxPriceBrandsByCategoryNameResponse;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PriceMapper {

    List<LowestPriceByCategoryResponse.Category> toResponseCategories(List<Product> source);

    @Mapping(source = "category.categoryName", target = "categoryName")
    @Mapping(source = "brand.brandName", target = "brandName")
    @Mapping(source = "productPrice", target = "productPrice", qualifiedByName = "longToString")
    LowestPriceByCategoryResponse.Category toResponseCategory(Product source);

    List<LowestTotalPriceBrandWithCategoryDetailsResponse.CategoryDetail> toResponseCategoryDetails(List<Product> source);

    @Mapping(source = "category.categoryName", target = "categoryName")
    @Mapping(source = "productPrice", target = "productPrice", qualifiedByName = "longToString")
    LowestTotalPriceBrandWithCategoryDetailsResponse.CategoryDetail toResponseCategoryDetail(Product source);

    List<MinMaxPriceBrandsByCategoryNameResponse.Brand> toResponseBrands(List<Product> source);

    @Mapping(source = "brand.brandName", target = "brandName")
    @Mapping(source = "productPrice", target = "productPrice", qualifiedByName = "longToString")
    MinMaxPriceBrandsByCategoryNameResponse.Brand toResponseBrand(Product source);

    @Named("longToString")
    default String longToString(Long value) {
        return value == null ? null : String.format("%,d", value);
    }

}
