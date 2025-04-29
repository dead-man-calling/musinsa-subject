package com.musinsa.subject.service.facade.impl;

import com.musinsa.subject.entity.Category;
import com.musinsa.subject.entity.Product;
import com.musinsa.subject.mapper.PriceMapper;
import com.musinsa.subject.model.price.LowestPriceByCategoryResponse;
import com.musinsa.subject.model.price.LowestTotalPriceBrandWithCategoryDetailsResponse;
import com.musinsa.subject.model.price.MinMaxPriceBrandsByCategoryNameResponse;
import com.musinsa.subject.service.core.CategoryService;
import com.musinsa.subject.service.core.ProductService;
import com.musinsa.subject.service.facade.PriceFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PriceFacadeImpl implements PriceFacade {

    private final CategoryService categoryService;
    private final ProductService productService;
    private final PriceMapper priceMapper;

    public LowestPriceByCategoryResponse getLowestPriceByCategory() {
        List<Product> products = productService.getLowestPriceByCategory();

        long totalPrice = products.stream().mapToLong(Product::getProductPrice).sum();

        return LowestPriceByCategoryResponse.builder()
                .categoryLowestPrices(priceMapper.toResponseCategories(products))
                .totalPrice(String.format("%,d", totalPrice))
                .build();
    }

    public LowestTotalPriceBrandWithCategoryDetailsResponse getLowestTotalPriceBrandWithCategoryDetails() {
        List<Product> products = productService.getLowestPriceProductByBrandAndCategory();

        Map<Long, Long> brandWithTotalPrices =
                products.stream()
                        .collect(Collectors.groupingBy(
                                p -> p.getBrand().getBrandId(),
                                Collectors.summingLong(Product::getProductPrice)
                        ));

        Map.Entry<Long, Long> minTotalPriceBrand =
                brandWithTotalPrices.entrySet().stream()
                        .min(Map.Entry.comparingByValue())
                        .orElseThrow();

        long brandId = minTotalPriceBrand.getKey();
        long totalPrice = minTotalPriceBrand.getValue();

        List<Product> brandProducts =
                products.stream()
                        .filter(p -> p.getBrand().getBrandId().equals(brandId))
                        .toList();

        String brandName = brandProducts.getFirst().getBrand().getBrandName(); // 브랜드의 카테고리에는 최소 1개의 상품은 존재한다.

        return new LowestTotalPriceBrandWithCategoryDetailsResponse(
                LowestTotalPriceBrandWithCategoryDetailsResponse.LowestPrice.builder()
                        .brandName(brandName)
                        .categories(priceMapper.toResponseCategoryDetails(brandProducts))
                        .totalPrice(String.format("%,d", totalPrice))
                        .build()
        );
    }

    public MinMaxPriceBrandsByCategoryNameResponse getMinMaxPriceBrandsByCategoryName(String categoryName) {
        Category category = categoryService.getCategoryByCategoryName(categoryName);

        List<Product> minPriceProducts = productService.getMinPriceProductInCategory(category.getCategoryId());
        List<Product> maxPriceProducts = productService.getMaxPriceProductInCategory(category.getCategoryId());

        return MinMaxPriceBrandsByCategoryNameResponse.builder()
                .categoryName(categoryName)
                .minPriceBrands(priceMapper.toResponseBrands(minPriceProducts))
                .maxPriceBrands(priceMapper.toResponseBrands(maxPriceProducts))
                .build();
    }

}
