package com.musinsa.subject.service.facade;

import com.musinsa.subject.model.brand.BrandRequest;
import com.musinsa.subject.model.brand.BrandResponse;

public interface BrandFacade {

    BrandResponse createBrand(BrandRequest request);

    BrandResponse getBrand(long brandId);

    BrandResponse updateBrand(long brandId, BrandRequest request);

    void deleteBrand(long brandId);

}
