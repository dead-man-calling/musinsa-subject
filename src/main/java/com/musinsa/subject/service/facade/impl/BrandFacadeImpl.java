package com.musinsa.subject.service.facade.impl;

import com.musinsa.subject.mapper.BrandMapper;
import com.musinsa.subject.model.brand.BrandRequest;
import com.musinsa.subject.model.brand.BrandResponse;
import com.musinsa.subject.service.core.BrandService;
import com.musinsa.subject.service.facade.BrandFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrandFacadeImpl implements BrandFacade {

    private final BrandService brandService;
    private final BrandMapper brandMapper;

    public BrandResponse createBrand(BrandRequest request) {
        return brandMapper.toResponseBrand(brandService.createBrand(request));
    }

    public BrandResponse getBrand(long brandId) {
        return brandMapper.toResponseBrand(brandService.getBrand(brandId));
    }

    public BrandResponse updateBrand(long brandId, BrandRequest request) {
        return brandMapper.toResponseBrand(brandService.updateBrand(brandId, request));
    }

    public void deleteBrand(long brandId) {
        brandService.deleteBrand(brandId);
    }
    
}
