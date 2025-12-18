package com.musinsa.subject.config

import com.musinsa.subject.entity.Brand
import com.musinsa.subject.entity.Category
import com.musinsa.subject.entity.Product
import com.musinsa.subject.repository.BrandRepository
import com.musinsa.subject.repository.CategoryRepository
import com.musinsa.subject.repository.ProductRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Profile("local")
@Component
class DataInitializer(
    private val categoryRepository: CategoryRepository,
    private val brandRepository: BrandRepository,
    private val productRepository: ProductRepository
) : CommandLineRunner {

    private val categoryNames = arrayOf(
        "상의",
        "아우터",
        "바지",
        "스니커즈",
        "가방",
        "모자",
        "양말",
        "액세서리"
    )

    private val brandNames = arrayOf(
        "A", "B", "C", "D", "E", "F", "G", "H", "I"
    )

    private val productPrices = arrayOf(
        // 상의    아우터    바지   스니커즈  가방    모자    양말   액세서리
        arrayOf(11200L, 5500L, 4200L, 9000L, 2000L, 1700L, 1800L, 2300L), // A
        arrayOf(10500L, 5900L, 3800L, 9100L, 2100L, 2000L, 2000L, 2200L), // B
        arrayOf(10000L, 6200L, 3300L, 9200L, 2200L, 1900L, 2200L, 2100L), // C
        arrayOf(10100L, 5100L, 3000L, 9500L, 2500L, 1500L, 2400L, 2000L), // D
        arrayOf(10700L, 5000L, 3800L, 9900L, 2300L, 1800L, 2100L, 2100L), // E
        arrayOf(11200L, 7200L, 4000L, 9300L, 2100L, 1600L, 2300L, 1900L), // F
        arrayOf(10500L, 5800L, 3900L, 9000L, 2200L, 1700L, 2100L, 2000L), // G
        arrayOf(10800L, 6300L, 3100L, 9700L, 2100L, 1600L, 2000L, 2000L), // H
        arrayOf(11400L, 6700L, 3200L, 9500L, 2400L, 1700L, 1700L, 2400L)  // I
    )

    private fun createCategories() {
        val categories = categoryNames.map { Category(categoryName = it) }
        categoryRepository.saveAll(categories)
    }

    private fun createBrands() {
        val brands = brandNames.map { Brand(brandName = it) }
        brandRepository.saveAll(brands)
    }

    private fun createProducts() {
        val brands = brandRepository.findAllByOrderByBrandId()
        val categories = categoryRepository.findAllByOrderByCategoryId()

        val products = brands.flatMapIndexed { i, brand ->
            categories.mapIndexed { j, category ->
                Product(
                    productPrice = productPrices[i][j],
                    brand = brand,
                    category = category
                )
            }
        }

        productRepository.saveAll(products)
    }

    override fun run(vararg args: String) {
        createCategories()
        createBrands()
        createProducts()
    }
}
