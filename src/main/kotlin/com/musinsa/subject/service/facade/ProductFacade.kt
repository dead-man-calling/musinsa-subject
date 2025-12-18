package com.musinsa.subject.service.facade

import com.musinsa.subject.model.product.ProductRequest
import com.musinsa.subject.model.product.ProductResponse

interface ProductFacade {
    fun createProduct(request: ProductRequest): ProductResponse
    fun getProduct(productId: Long): ProductResponse
    fun updateProduct(productId: Long, request: ProductRequest): ProductResponse
    fun deleteProduct(productId: Long)
}
