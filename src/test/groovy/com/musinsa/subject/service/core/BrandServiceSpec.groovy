package com.musinsa.subject.service.core

import com.musinsa.subject.entity.Brand
import com.musinsa.subject.exception.DomainException
import com.musinsa.subject.model.brand.BrandRequest
import com.musinsa.subject.repository.BrandRepository
import spock.lang.Specification

class BrandServiceSpec extends Specification {

    BrandRepository repository = Mock(BrandRepository)
    BrandService brandService = new BrandService(repository)

    def "브랜드 조회 - 존재하는 브랜드 ID로 조회 시 해당 브랜드를 반환한다"() {
        given:
        def brandId = 1L
        def brandName = "A"
        def brand = Brand.builder()
                .brandId(brandId)
                .brandName(brandName)
                .build()

        and:
        1 * repository.findById(brandId) >> Optional.of(brand)

        when:
        def result = brandService.getBrand(brandId)

        then:
        result.brandId == brandId
        result.brandName == brandName
    }

    def "브랜드 조회 - 존재하지 않는 브랜드 ID로 조회 시 예외가 발생한다"() {
        given:
        def brandId = 999L

        and:
        1 * repository.findById(brandId) >> Optional.empty()

        when:
        brandService.getBrand(brandId)

        then:
        thrown(DomainException)
    }

    def "브랜드 생성 - 유효한 요청으로 브랜드를 생성한다"() {
        given:
        def brandId = 1L
        def brandName = "A"

        and:
        def request = new BrandRequest()
        request.setBrandName(brandName)

        and:
        def savedBrand = Brand.builder()
                .brandId(brandId)
                .brandName(brandName)
                .build()

        and:
        1 * repository.save(_ as Brand) >> savedBrand

        when:
        def result = brandService.createBrand(request)

        then:
        result.brandId == brandId
        result.brandName == brandName
    }

    def "브랜드 수정 - 존재하는 브랜드의 이름을 수정한다"() {
        given:
        def brandId = 1L
        def oldBrandName = "A"
        def newBrandName = "B"

        and:
        def request = new BrandRequest()
        request.setBrandName(newBrandName)

        and:
        def existingBrand = Brand.builder()
                .brandId(brandId)
                .brandName(oldBrandName)
                .build()
        def updatedBrand = Brand.builder()
                .brandId(brandId)
                .brandName(newBrandName)
                .build()

        and:
        1 * repository.findById(brandId) >> Optional.of(existingBrand)
        1 * repository.save(_ as Brand) >> updatedBrand

        when:
        def result = brandService.updateBrand(brandId, request)

        then:
        result.brandId == brandId
        result.brandName == newBrandName
    }

    def "브랜드 수정 - 존재하지 않는 브랜드 ID로 수정 시 예외가 발생한다"() {
        given:
        def brandId = 999L
        def brandName = "A"

        and:
        def request = new BrandRequest()
        request.setBrandName(brandName)

        and:
        1 * repository.findById(brandId) >> Optional.empty()
        0 * repository.save(_)

        when:
        brandService.updateBrand(brandId, request)

        then:
        thrown(DomainException)
    }

    def "브랜드 삭제 - 브랜드 ID로 삭제를 요청한다"() {
        given:
        def brandId = 1L

        when:
        brandService.deleteBrand(brandId)

        then:
        1 * repository.deleteById(brandId)
    }

}
