package com.musinsa.subject.service.facade

import com.musinsa.subject.entity.Brand
import com.musinsa.subject.entity.Category
import com.musinsa.subject.entity.Product
import com.musinsa.subject.exception.DomainException
import com.musinsa.subject.exception.DomainExceptionType
import com.musinsa.subject.mapper.PriceMapper
import com.musinsa.subject.model.price.LowestPriceByCategoryResponse
import com.musinsa.subject.model.price.LowestTotalPriceBrandWithCategoryDetailsResponse
import com.musinsa.subject.model.price.MinMaxPriceBrandsByCategoryNameResponse
import com.musinsa.subject.service.core.CategoryService
import com.musinsa.subject.service.core.ProductService
import com.musinsa.subject.service.facade.impl.PriceFacadeImpl
import spock.lang.Specification

class PriceFacadeSpec extends Specification {

    CategoryService categoryService = Stub(CategoryService)
    ProductService productService = Stub(ProductService)
    PriceMapper priceMapper = Stub(PriceMapper)

    PriceFacade priceFacade

    def setup() {
        priceFacade = new PriceFacadeImpl(
                categoryService,
                productService,
                priceMapper
        )
    }

    def "getLowestPriceByCategory - 각 카테고리별 최저가 브랜드와 상품 가격과 총액을 조회한다"() {
        given: "카테고리별 최저가 상품 리스트"
        def brand1 = createBrand(1L, "A")
        def brand2 = createBrand(2L, "B")

        def category1 = createCategory(1L, "상의")
        def category2 = createCategory(2L, "바지")

        def product1 = createProduct(1L, 10000L, brand1, category1)
        def product2 = createProduct(2L, 20000L, brand2, category2)

        def products = [product1, product2]

        and:
        def categoryResponses = [
                new LowestPriceByCategoryResponse.Category("상의", "A", "10,000"),
                new LowestPriceByCategoryResponse.Category("바지", "B", "20,000")
        ]

        and:
        productService.getLowestPriceProductByCategory() >> products
        priceMapper.toResponseCategories(products) >> categoryResponses

        when:
        def result = priceFacade.getLowestPriceByCategory()

        then:
        result.categoryLowestPrices == categoryResponses
        result.totalPrice == "30,000"
    }

    def "getLowestTotalPriceBrandWithCategoryDetails - 전체 카테고리 상품 구매시 최저가 브랜드와 상품 정보를 조회한다"() {
        given: "브랜드와 카테고리별 최저가 상품 리스트"
        def brand1 = createBrand(1L, "A")
        def brand2 = createBrand(2L, "B")

        def category1 = createCategory(1L, "상의")
        def category2 = createCategory(2L, "바지")

        // 브랜드 A의 카테고리 별 최저가 상품들 (총합: 35,000원)
        def productA1 = createProduct(1L, 15000L, brand1, category1)
        def productA2 = createProduct(2L, 20000L, brand1, category2)

        // 브랜드 B의 카테고리 별 최저가 상품들 (총합: 30,000원) - 최저가 브랜드
        def productB1 = createProduct(3L, 12000L, brand2, category1)
        def productB2 = createProduct(4L, 18000L, brand2, category2)

        def allProducts = [productA1, productA2, productB1, productB2]

        and: "브랜드 B 상품에 대한 카테고리 응답 설정"
        def brandBProducts = [productB1, productB2]
        def categoryDetails = [
                new LowestTotalPriceBrandWithCategoryDetailsResponse.CategoryDetail("상의", "12,000"),
                new LowestTotalPriceBrandWithCategoryDetailsResponse.CategoryDetail("바지", "18,000")
        ]

        and:
        productService.getLowestPriceProductByBrandAndCategory() >> allProducts
        priceMapper.toResponseCategoryDetails(brandBProducts) >> categoryDetails

        when:
        def result = priceFacade.getLowestTotalPriceBrandWithCategoryDetails()

        then:
        result.lowestPrice.brandName == "B"
        result.lowestPrice.categories == categoryDetails
        result.lowestPrice.totalPrice == "30,000"
    }

    def "getMinMaxPriceBrandsByCategoryName - 특정 카테고리에서 최저가, 최고가 브랜드와 가격을 조회한다"() {
        given: "특정 카테고리 설정"
        def categoryName = "상의"
        def category = createCategory(1L, categoryName)

        and: "최저가 상품 설정"
        def minPriceBrand1 = createBrand(1L, "A")
        def minPriceBrand2 = createBrand(2L, "B")
        
        def minPriceProduct1 = createProduct(1L, 10000L, minPriceBrand1, category)
        def minPriceProduct2 = createProduct(2L, 10000L, minPriceBrand2, category)
        
        def minPriceProducts = [minPriceProduct1, minPriceProduct2]

        and: "최고가 상품 설정"
        def maxPriceBrand = createBrand(3L, "C")
        def maxPriceProduct = createProduct(3L, 50000L, maxPriceBrand, category)
        def maxPriceProducts = [maxPriceProduct]

        and: "브랜드 응답 설정"
        def minPriceBrandResponses = [
                new MinMaxPriceBrandsByCategoryNameResponse.Brand("A", "10,000"),
                new MinMaxPriceBrandsByCategoryNameResponse.Brand("B", "10,000")
        ]
        def maxPriceBrandResponses = [
                new MinMaxPriceBrandsByCategoryNameResponse.Brand("C", "50,000")
        ]

        and:
        categoryService.getCategoryByCategoryName(categoryName) >> category

        productService.getMinPriceProductInCategory(category.categoryId) >> minPriceProducts
        productService.getMaxPriceProductInCategory(category.categoryId) >> maxPriceProducts

        priceMapper.toResponseBrands(minPriceProducts) >> minPriceBrandResponses
        priceMapper.toResponseBrands(maxPriceProducts) >> maxPriceBrandResponses

        when:
        def result = priceFacade.getMinMaxPriceBrandsByCategoryName(categoryName)

        then:
        result.categoryName == categoryName
        result.minPriceBrands == minPriceBrandResponses
        result.maxPriceBrands == maxPriceBrandResponses
    }

    def "getMinMaxPriceBrandsByCategoryName - 존재하지 않는 카테고리명으로 조회시 예외가 발생한다"() {
        given:
        def nonExistentCategoryName = "가전제품"

        and:
        categoryService.getCategoryByCategoryName(nonExistentCategoryName) >> {
            throw new DomainException(DomainExceptionType.CATEGORY_NOT_FOUND)
        }

        when:
        priceFacade.getMinMaxPriceBrandsByCategoryName(nonExistentCategoryName)

        then:
        def exception = thrown(DomainException)
        exception.type == DomainExceptionType.CATEGORY_NOT_FOUND
    }

    private static Brand createBrand(Long id, String name) {
        return Brand.builder()
                .brandId(id)
                .brandName(name)
                .build()
    }

    private static Category createCategory(Long id, String name) {
        return Category.builder()
                .categoryId(id)
                .categoryName(name)
                .build()
    }

    private static Product createProduct(Long id, Long price, Brand brand, Category category) {
        return Product.builder()
                .productId(id)
                .productPrice(price)
                .brand(brand)
                .category(category)
                .build()
    }

}
