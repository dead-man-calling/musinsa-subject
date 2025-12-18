package com.musinsa.subject.service.facade

import com.musinsa.subject.model.brand.BrandRequest
import com.musinsa.subject.model.brand.BrandResponse

interface BrandFacade {
    fun createBrand(request: BrandRequest): BrandResponse
    fun getBrand(brandId: Long): BrandResponse
    fun updateBrand(brandId: Long, request: BrandRequest): BrandResponse
    fun deleteBrand(brandId: Long)
}
