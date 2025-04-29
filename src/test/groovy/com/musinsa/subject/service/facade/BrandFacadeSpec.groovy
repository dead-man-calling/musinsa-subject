package com.musinsa.subject.service.facade

import com.musinsa.subject.entity.Brand
import com.musinsa.subject.exception.DomainException
import com.musinsa.subject.exception.DomainExceptionType
import com.musinsa.subject.mapper.BrandMapper
import com.musinsa.subject.model.brand.BrandRequest
import com.musinsa.subject.model.brand.BrandResponse
import com.musinsa.subject.service.core.BrandService
import com.musinsa.subject.service.facade.impl.BrandFacadeImpl
import spock.lang.Specification

class BrandFacadeSpec extends Specification {

    BrandService brandService = Stub(BrandService)
    BrandMapper brandMapper = Stub(BrandMapper)

    BrandFacade brandFacade

    Long brandId = 1L
    String brandName = "A"

    Brand brand

    def setup() {
        brandFacade = new BrandFacadeImpl(
                brandService,
                brandMapper
        )

        brand = Brand.builder()
                .brandId(brandId)
                .brandName(brandName)
                .build()
    }

    def "createBrand - 브랜드 생성 요청을 처리하고 응답을 반환한다"() {
        given:
        def request = new BrandRequest(
                brandName: brandName
        )
        def expectedResponse = new BrandResponse(
                brandId: brandId,
                brandName: brandName
        )

        and:
        brandService.createBrand(request) >> brand
        brandMapper.toResponseBrand(brand) >> expectedResponse

        when:
        def result = brandFacade.createBrand(request)

        then:
        result == expectedResponse
    }

    def "getBrand - 브랜드 ID로 브랜드를 조회하고 응답을 반환한다"() {
        given:
        def expectedResponse = new BrandResponse(
                brandId: brandId,
                brandName: brandName
        )

        and:
        brandService.getBrand(brandId) >> brand
        brandMapper.toResponseBrand(brand) >> expectedResponse

        when:
        def result = brandFacade.getBrand(brandId)

        then:
        result == expectedResponse
    }

    def "updateBrand - 브랜드를 업데이트하고 응답을 반환한다"() {
        given:
        def newBrandName = "B"

        def request = new BrandRequest(
                brandName: newBrandName
        )
        def updatedBrand = new Brand(
                brandId: brandId,
                brandName: newBrandName
        )
        def expectedResponse = new BrandResponse(
                brandId: brandId,
                brandName: newBrandName
        )

        and:
        brandService.updateBrand(brandId, request) >> updatedBrand
        brandMapper.toResponseBrand(updatedBrand) >> expectedResponse

        when:
        def result = brandFacade.updateBrand(brandId, request)

        then:
        result == expectedResponse
    }

    def "deleteBrand - 브랜드 삭제 요청을 처리한다"() {
        when:
        brandFacade.deleteBrand(brandId)

        then:
        noExceptionThrown()
    }

    def "getBrand - 존재하지 않는 브랜드 ID로 조회시 예외가 발생한다"() {
        given:
        def nonExistentBrandId = 999L

        and:
        brandService.getBrand(nonExistentBrandId) >> { throw new DomainException(DomainExceptionType.BRAND_NOT_FOUND) }

        when:
        brandFacade.getBrand(nonExistentBrandId)

        then:
        def exception = thrown(DomainException)
        exception.type == DomainExceptionType.BRAND_NOT_FOUND
    }

}
