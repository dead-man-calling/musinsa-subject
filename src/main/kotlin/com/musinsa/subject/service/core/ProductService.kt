package com.musinsa.subject.service.core

import com.musinsa.subject.entity.Brand
import com.musinsa.subject.entity.Category
import com.musinsa.subject.entity.Product
import com.musinsa.subject.exception.DomainException
import com.musinsa.subject.exception.DomainExceptionType
import com.musinsa.subject.model.product.ProductRequest
import com.musinsa.subject.repository.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductService(
    private val repository: ProductRepository
) {

    fun getProduct(productId: Long): Product {
        return repository.findById(productId)
            .orElseThrow {
                DomainException(type = DomainExceptionType.PRODUCT_NOT_FOUND)
            }
    }

    fun createProduct(productPrice: Long, brand: Brand, category: Category): Product {
        return repository.save(
            Product(
                productPrice = productPrice,
                brand = brand,
                category = category
            )
        )
    }

    @Transactional
    fun updateProduct(productId: Long, request: ProductRequest): Product {
        val product = getProduct(productId)
        product.productPrice = request.productPrice
        return repository.save(product)
    }

    fun deleteProduct(productId: Long) {
        repository.deleteById(productId)
    }

    fun getLowestPriceProductByCategory(): List<Product> {
        return repository.getLowestPriceProductByCategory()
    }

    fun getLowestPriceProductByBrandAndCategory(): List<Product> {
        return repository.getLowestPriceProductByBrandAndCategory()
    }

    fun getMinPriceProductInCategory(categoryId: Long): List<Product> {
        return repository.getMinPriceProductInCategory(categoryId)
    }

    fun getMaxPriceProductInCategory(categoryId: Long): List<Product> {
        return repository.getMaxPriceProductInCategory(categoryId)
    }
}
