package com.musinsa.subject.repository

import com.musinsa.subject.entity.Product
import com.musinsa.subject.entity.QProduct
import com.querydsl.jpa.JPAExpressions
import com.querydsl.jpa.impl.JPAQueryFactory

class ProductRepositoryImpl(
    private val factory: JPAQueryFactory
) : ProductRepositoryCustom {

    override fun getLowestPriceProductByCategory(): List<Product> {
        val product = QProduct.product
        val subProduct = QProduct("subProduct")

        return factory.selectFrom(product)
            .leftJoin(product.brand).fetchJoin()
            .leftJoin(product.category).fetchJoin()
            .where(
                JPAExpressions
                    .select(subProduct.productPrice.min())
                    .from(subProduct)
                    .where(subProduct.category.categoryId.eq(product.category.categoryId))
                    .eq(product.productPrice)
            )
            .fetch()
            .associateBy {
                it.category.categoryId ?: error("Category ID must not be null for product ${it.productId}")
            }
            .values
            .toList()
    }

    override fun getLowestPriceProductByBrandAndCategory(): List<Product> {
        val product = QProduct.product
        val subProduct = QProduct("subProduct")

        return factory.selectFrom(product)
            .leftJoin(product.brand).fetchJoin()
            .leftJoin(product.category).fetchJoin()
            .where(
                JPAExpressions
                    .select(subProduct.productPrice.min())
                    .from(subProduct)
                    .where(subProduct.brand.brandId.eq(product.brand.brandId))
                    .where(subProduct.category.categoryId.eq(product.category.categoryId))
                    .eq(product.productPrice)
            )
            .fetch()
            .associateBy {
                val brandId = it.brand.brandId ?: error("Brand ID must not be null for product ${it.productId}")
                val categoryId = it.category.categoryId ?: error("Category ID must not be null for product ${it.productId}")
                Pair(brandId, categoryId)
            }
            .values
            .toList()
    }

    override fun getMinPriceProductInCategory(categoryId: Long): List<Product> {
        val product = QProduct.product
        val subProduct = QProduct("subProduct")

        return factory.selectFrom(product)
            .leftJoin(product.brand).fetchJoin()
            .leftJoin(product.category).fetchJoin()
            .where(
                JPAExpressions
                    .select(subProduct.productPrice.min())
                    .from(subProduct)
                    .where(subProduct.category.categoryId.eq(categoryId))
                    .eq(product.productPrice)
            )
            .fetch()
    }

    override fun getMaxPriceProductInCategory(categoryId: Long): List<Product> {
        val product = QProduct.product
        val subProduct = QProduct("subProduct")

        return factory.selectFrom(product)
            .leftJoin(product.brand).fetchJoin()
            .leftJoin(product.category).fetchJoin()
            .where(
                JPAExpressions
                    .select(subProduct.productPrice.max())
                    .from(subProduct)
                    .where(subProduct.category.categoryId.eq(categoryId))
                    .eq(product.productPrice)
            )
            .fetch()
    }
}
