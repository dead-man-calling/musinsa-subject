package com.musinsa.subject.service.core

import com.musinsa.subject.entity.Brand
import com.musinsa.subject.exception.DomainException
import com.musinsa.subject.exception.DomainExceptionType
import com.musinsa.subject.model.brand.BrandRequest
import com.musinsa.subject.repository.BrandRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BrandService(
    private val repository: BrandRepository
) {

    fun getBrand(brandId: Long): Brand {
        return repository.findById(brandId)
            .orElseThrow {
                DomainException(type = DomainExceptionType.BRAND_NOT_FOUND)
            }
    }

    fun createBrand(request: BrandRequest): Brand {
        return repository.save(Brand(brandName = request.brandName))
    }

    @Transactional
    fun updateBrand(brandId: Long, request: BrandRequest): Brand {
        val brand = getBrand(brandId)
        brand.brandName = request.brandName
        return repository.save(brand)
    }

    fun deleteBrand(brandId: Long) {
        repository.deleteById(brandId)
    }
}
