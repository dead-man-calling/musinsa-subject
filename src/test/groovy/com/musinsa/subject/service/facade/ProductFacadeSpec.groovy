package com.musinsa.subject.service.facade

import com.musinsa.subject.entity.Brand
import com.musinsa.subject.entity.Category
import com.musinsa.subject.entity.Product
import com.musinsa.subject.exception.DomainException
import com.musinsa.subject.exception.DomainExceptionType
import com.musinsa.subject.mapper.ProductMapper
import com.musinsa.subject.model.product.ProductRequest
import com.musinsa.subject.model.product.ProductResponse
import com.musinsa.subject.service.core.BrandService
import com.musinsa.subject.service.core.CategoryService
import com.musinsa.subject.service.core.ProductService
import com.musinsa.subject.service.facade.impl.ProductFacadeImpl
import spock.lang.Specification

class ProductFacadeSpec extends Specification {

    BrandService brandService = Stub(BrandService)
    CategoryService categoryService = Stub(CategoryService)
    ProductService productService = Stub(ProductService)
    ProductMapper productMapper = Stub(ProductMapper)

    ProductFacade productFacade

    Long productId = 1L
    Long productPrice = 10000L
    Long brandId = 1L
    Long categoryId = 1L

    Brand brand
    Category category
    Product product

    def setup() {
        productFacade = new ProductFacadeImpl(
                brandService,
                categoryService,
                productService,
                productMapper
        )

        brand = Brand.builder()
                .brandId(brandId)
                .brandName("A")
                .build()

        category = Category.builder()
                .categoryId(categoryId)
                .categoryName("상의")
                .build()

        product = Product.builder()
                .productId(productId)
                .productPrice(productPrice)
                .brand(brand)
                .category(category)
                .build()
    }

    def "createProduct - 상품 생성 요청을 처리하고 응답을 반환한다"() {
        given:
        def request = new ProductRequest(
                brandId: brandId,
                categoryId: categoryId,
                productPrice: productPrice
        )
        def expectedResponse = new ProductResponse(
                productId: productId,
                productPrice: productPrice
        )

        and:
        brandService.getBrand(brandId) >> brand
        categoryService.getCategory(categoryId) >> category
        productService.createProduct(productPrice, brand, category) >> product
        productMapper.toResponseProduct(product) >> expectedResponse

        when:
        def result = productFacade.createProduct(request)

        then:
        result == expectedResponse
    }

    def "getProduct - 상품 ID로 상품을 조회하고 응답을 반환한다"() {
        given:
        def expectedResponse = new ProductResponse(
                productId: productId,
                productPrice: productPrice
        )

        and:
        productService.getProduct(productId) >> product
        productMapper.toResponseProduct(product) >> expectedResponse

        when:
        def result = productFacade.getProduct(productId)

        then:
        result == expectedResponse
    }

    def "updateProduct - 상품을 업데이트하고 응답을 반환한다"() {
        given:
        def newProductPrice = 30000L

        def request = new ProductRequest(
                productPrice: newProductPrice
        )
        def updatedProduct = new Product(
                productId: productId,
                productPrice: newProductPrice,
                brand: brand,
                category: category
        )
        def expectedResponse = new ProductResponse(
                productId: productId,
                productPrice: newProductPrice
        )

        and:
        productService.updateProduct(productId, request) >> updatedProduct
        productMapper.toResponseProduct(updatedProduct) >> expectedResponse

        when:
        def result = productFacade.updateProduct(productId, request)

        then:
        result == expectedResponse
    }

    def "deleteProduct - 상품 삭제 요청을 처리한다"() {
        when:
        productFacade.deleteProduct(productId)

        then:
        noExceptionThrown()
    }

    def "createProduct - 브랜드가 없을 경우 예외가 발생한다"() {
        given:
        def nonExistentBrandId = 999L

        def request = new ProductRequest(
                brandId: nonExistentBrandId,
                categoryId: categoryId,
                productPrice: productPrice
        )

        and:
        brandService.getBrand(nonExistentBrandId) >> { throw new DomainException(DomainExceptionType.BRAND_NOT_FOUND) }

        when:
        productFacade.createProduct(request)

        then:
        def exception = thrown(DomainException)
        exception.type == DomainExceptionType.BRAND_NOT_FOUND
    }

    def "createProduct - 카테고리가 없을 경우 예외가 발생한다"() {
        given:
        def nonExistentCategoryId = 999L

        def request = new ProductRequest(
                brandId: brandId,
                categoryId: nonExistentCategoryId,
                productPrice: productPrice
        )

        and:
        brandService.getBrand(brandId) >> brand
        categoryService.getCategory(nonExistentCategoryId) >> { throw new DomainException(DomainExceptionType.CATEGORY_NOT_FOUND) }

        when:
        productFacade.createProduct(request)

        then:
        def exception = thrown(DomainException)
        exception.type == DomainExceptionType.CATEGORY_NOT_FOUND
    }

}
