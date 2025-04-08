package com.musinsa.subject.service.core;

import com.musinsa.subject.entity.Brand;
import com.musinsa.subject.model.brand.BrandRequest;
import com.musinsa.subject.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository repository;

    public Brand getBrand(long brandId) {
        return repository.findById(brandId).orElseThrow();
    }

    public Brand createBrand(BrandRequest request) {
        return repository.save(Brand.builder().brandName(request.getBrandName()).build());
    }

    public Brand updateBrand(long brandId, BrandRequest request) {
        Brand brand = repository.findById(brandId).orElseThrow();

        brand.setBrandName(request.getBrandName());

        return repository.save(brand);
    }

    public void deleteBrand(long brandId) {
        repository.deleteById(brandId);
    }

}
