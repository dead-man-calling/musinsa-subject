package com.musinsa.subject.service.facade.impl

import com.musinsa.subject.config.CacheName
import com.musinsa.subject.mapper.ProductMapper
import com.musinsa.subject.model.product.ProductRequest
import com.musinsa.subject.model.product.ProductResponse
import com.musinsa.subject.service.core.BrandService
import com.musinsa.subject.service.core.CategoryService
import com.musinsa.subject.service.core.ProductService
import com.musinsa.subject.service.facade.ProductFacade
import org.springframework.cache.annotation.CacheEvict
import org.springframework.stereotype.Service

@Service
class ProductFacadeImpl(
    private val brandService: BrandService,
    private val categoryService: CategoryService,
    private val productService: ProductService,
    private val productMapper: ProductMapper
) : ProductFacade {

    @CacheEvict(
        cacheNames = [
            CacheName.LOWEST_PRICE_BY_CATEGORY,
            CacheName.LOWEST_TOTAL_PRICE_BRAND_WITH_CATEGORY_DETAILS,
            CacheName.MIN_MAX_PRICE_BRANDS_BY_CATEGORY_NAME
        ],
        allEntries = true
    )
    override fun createProduct(request: ProductRequest): ProductResponse {
        val brand = brandService.getBrand(request.brandId)
        val category = categoryService.getCategory(request.categoryId)

        val product = productService.createProduct(request.productPrice, brand, category)

        return productMapper.toResponseProduct(product)
    }

    override fun getProduct(productId: Long): ProductResponse {
        return productMapper.toResponseProduct(productService.getProduct(productId))
    }

    @CacheEvict(
        cacheNames = [
            CacheName.LOWEST_PRICE_BY_CATEGORY,
            CacheName.LOWEST_TOTAL_PRICE_BRAND_WITH_CATEGORY_DETAILS,
            CacheName.MIN_MAX_PRICE_BRANDS_BY_CATEGORY_NAME
        ],
        allEntries = true
    )
    override fun updateProduct(productId: Long, request: ProductRequest): ProductResponse {
        return productMapper.toResponseProduct(productService.updateProduct(productId, request))
    }

    @CacheEvict(
        cacheNames = [
            CacheName.LOWEST_PRICE_BY_CATEGORY,
            CacheName.LOWEST_TOTAL_PRICE_BRAND_WITH_CATEGORY_DETAILS,
            CacheName.MIN_MAX_PRICE_BRANDS_BY_CATEGORY_NAME
        ],
        allEntries = true
    )
    override fun deleteProduct(productId: Long) {
        productService.deleteProduct(productId)
    }
}
