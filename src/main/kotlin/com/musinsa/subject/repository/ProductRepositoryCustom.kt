package com.musinsa.subject.repository

import com.musinsa.subject.entity.Product

interface ProductRepositoryCustom {
    fun getLowestPriceProductByCategory(): List<Product>
    fun getLowestPriceProductByBrandAndCategory(): List<Product>
    fun getMinPriceProductInCategory(categoryId: Long): List<Product>
    fun getMaxPriceProductInCategory(categoryId: Long): List<Product>
}
