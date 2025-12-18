package com.musinsa.subject.service.facade.impl

import com.musinsa.subject.config.CacheName
import com.musinsa.subject.mapper.PriceMapper
import com.musinsa.subject.model.price.LowestPriceByCategoryResponse
import com.musinsa.subject.model.price.LowestTotalPriceBrandWithCategoryDetailsResponse
import com.musinsa.subject.model.price.MinMaxPriceBrandsByCategoryNameResponse
import com.musinsa.subject.service.core.CategoryService
import com.musinsa.subject.service.core.ProductService
import com.musinsa.subject.service.facade.PriceFacade
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class PriceFacadeImpl(
    private val categoryService: CategoryService,
    private val productService: ProductService,
    private val priceMapper: PriceMapper
) : PriceFacade {

    @Cacheable(cacheNames = [CacheName.LOWEST_PRICE_BY_CATEGORY])
    override fun getLowestPriceByCategory(): LowestPriceByCategoryResponse {
        val products = productService.getLowestPriceProductByCategory()

        val totalPrice = products.sumOf { it.productPrice }

        return LowestPriceByCategoryResponse(
            categoryLowestPrices = priceMapper.toResponseCategories(products),
            totalPrice = String.format("%,d", totalPrice)
        )
    }

    @Cacheable(cacheNames = [CacheName.LOWEST_TOTAL_PRICE_BRAND_WITH_CATEGORY_DETAILS])
    override fun getLowestTotalPriceBrandWithCategoryDetails(): LowestTotalPriceBrandWithCategoryDetailsResponse {
        val products = productService.getLowestPriceProductByBrandAndCategory()

        val brandWithTotalPrices = products
            .groupBy {
                it.brand.brandId ?: error("Brand ID must not be null for product ${it.productId}")
            }
            .mapValues { (_, prods) -> prods.sumOf { it.productPrice } }

        val minTotalPriceBrand = brandWithTotalPrices.minBy { it.value }

        val brandId = minTotalPriceBrand.key
        val totalPrice = minTotalPriceBrand.value

        val brandProducts = products.filter { it.brand.brandId == brandId }

        val brandName = brandProducts.first().brand.brandName

        return LowestTotalPriceBrandWithCategoryDetailsResponse(
            lowestPrice = LowestTotalPriceBrandWithCategoryDetailsResponse.LowestPrice(
                brandName = brandName,
                categories = priceMapper.toResponseCategoryDetails(brandProducts),
                totalPrice = String.format("%,d", totalPrice)
            )
        )
    }

    @Cacheable(cacheNames = [CacheName.MIN_MAX_PRICE_BRANDS_BY_CATEGORY_NAME], key = "#categoryName")
    override fun getMinMaxPriceBrandsByCategoryName(categoryName: String): MinMaxPriceBrandsByCategoryNameResponse {
        val category = categoryService.getCategoryByCategoryName(categoryName)
        val categoryId = category.categoryId ?: error("Category ID must not be null for category: $categoryName")

        val minPriceProducts = productService.getMinPriceProductInCategory(categoryId)
        val maxPriceProducts = productService.getMaxPriceProductInCategory(categoryId)

        return MinMaxPriceBrandsByCategoryNameResponse(
            categoryName = categoryName,
            minPriceBrands = priceMapper.toResponseBrands(minPriceProducts),
            maxPriceBrands = priceMapper.toResponseBrands(maxPriceProducts)
        )
    }
}
