package com.musinsa.subject.service.facade.impl

import com.musinsa.subject.mapper.BrandMapper
import com.musinsa.subject.model.brand.BrandRequest
import com.musinsa.subject.model.brand.BrandResponse
import com.musinsa.subject.service.core.BrandService
import com.musinsa.subject.service.facade.BrandFacade
import org.springframework.stereotype.Service

@Service
class BrandFacadeImpl(
    private val brandService: BrandService,
    private val brandMapper: BrandMapper
) : BrandFacade {

    override fun createBrand(request: BrandRequest): BrandResponse {
        return brandMapper.toResponseBrand(brandService.createBrand(request))
    }

    override fun getBrand(brandId: Long): BrandResponse {
        return brandMapper.toResponseBrand(brandService.getBrand(brandId))
    }

    override fun updateBrand(brandId: Long, request: BrandRequest): BrandResponse {
        return brandMapper.toResponseBrand(brandService.updateBrand(brandId, request))
    }

    override fun deleteBrand(brandId: Long) {
        brandService.deleteBrand(brandId)
    }
}
