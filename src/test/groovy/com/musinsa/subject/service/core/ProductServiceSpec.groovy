package com.musinsa.subject.service.core

import com.musinsa.subject.entity.Brand
import com.musinsa.subject.entity.Category
import com.musinsa.subject.entity.Product
import com.musinsa.subject.exception.DomainException
import com.musinsa.subject.model.product.ProductRequest
import com.musinsa.subject.repository.ProductRepository
import spock.lang.Specification

class ProductServiceSpec extends Specification {

    ProductRepository repository = Mock(ProductRepository)
    ProductService productService = new ProductService(repository)

    Brand brand
    Category category

    def setup() {
        brand = Brand.builder()
                .brandId(1L)
                .brandName("A")
                .build()

        category = Category.builder()
                .categoryId(1L)
                .categoryName("상의")
                .build()
    }

    def "상품 조회 - 존재하는 상품 ID로 조회 시 해당 상품을 반환한다"() {
        given:
        def productId = 1L
        def productPrice = 10000L
        def product = Product.builder()
                .productId(productId)
                .productPrice(productPrice)
                .brand(brand)
                .category(category)
                .build()

        and:
        1 * repository.findById(productId) >> Optional.of(product)

        when:
        def result = productService.getProduct(productId)

        then:
        result.productId == productId
        result.productPrice == productPrice
    }

    def "상품 조회 - 존재하지 않는 상품 ID로 조회 시 예외가 발생한다"() {
        given:
        def productId = 999L

        and:
        1 * repository.findById(productId) >> Optional.empty()

        when:
        productService.getProduct(productId)

        then:
        thrown(DomainException)
    }

    def "상품 생성 - 유효한 요청으로 상품을 생성한다"() {
        given:
        def productId = 1L
        def productPrice = 10000L

        and:
        def savedProduct = Product.builder()
                .productId(productId)
                .productPrice(productPrice)
                .brand(brand)
                .category(category)
                .build()

        and:
        1 * repository.save(_ as Product) >> savedProduct

        when:
        def result = productService.createProduct(productPrice, brand, category)

        then:
        result.productId == productId
        result.productPrice == productPrice
    }

    def "상품 수정 - 존재하는 상품의 가격을 수정한다"() {
        given:
        def productId = 1L
        def oldProductPrice = Long.valueOf(10000L)
        def newProductPrice = Long.valueOf(30000L)

        and:
        def request = new ProductRequest()
        request.setProductPrice(newProductPrice)

        and:
        def existingProduct = Product.builder()
                .productId(productId)
                .productPrice(oldProductPrice)
                .brand(brand)
                .category(category)
                .build()
        def updatedProduct = Product.builder()
                .productId(productId)
                .productPrice(newProductPrice)
                .brand(brand)
                .category(category)
                .build()

        and:
        1 * repository.findById(productId) >> Optional.of(existingProduct)
        1 * repository.save(_ as Product) >> updatedProduct

        when:
        def result = productService.updateProduct(productId, request)

        then:
        result.productId == productId
        result.productPrice == newProductPrice
    }

    def "상품 수정 - 존재하지 않는 상품 ID로 수정 시 예외가 발생한다"() {
        given:
        def productId = 999L
        def productPrice = 30000L

        and:
        def request = new ProductRequest()
        request.setProductPrice(productPrice)

        and:
        1 * repository.findById(productId) >> Optional.empty()
        0 * repository.save(_)

        when:
        productService.updateProduct(productId, request)

        then:
        thrown(DomainException)
    }

    def "상품 삭제 - 상품 ID로 삭제을 요청한다"() {
        given:
        def productId = 1L

        when:
        productService.deleteProduct(productId)

        then:
        1 * repository.deleteById(productId)
    }

}
