package com.musinsa.subject.service.core;

import com.musinsa.subject.entity.Brand;
import com.musinsa.subject.exception.DomainException;
import com.musinsa.subject.exception.DomainExceptionType;
import com.musinsa.subject.model.brand.BrandRequest;
import com.musinsa.subject.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository repository;

    public Brand getBrand(long brandId) {
        return repository.findById(brandId)
                .orElseThrow(() ->
                        DomainException.builder()
                                .type(DomainExceptionType.BRAND_NOT_FOUND)
                                .build()
                );
    }

    public Brand createBrand(BrandRequest request) {
        return repository.save(Brand.builder().brandName(request.getBrandName()).build());
    }

    @Transactional
    public Brand updateBrand(long brandId, BrandRequest request) {
        Brand brand = this.getBrand(brandId);

        brand.setBrandName(request.getBrandName());

        return repository.save(brand);
    }

    public void deleteBrand(long brandId) {
        repository.deleteById(brandId);
    }

}
