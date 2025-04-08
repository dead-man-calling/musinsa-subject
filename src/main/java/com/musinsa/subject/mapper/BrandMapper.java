package com.musinsa.subject.mapper;

import com.musinsa.subject.entity.Brand;
import com.musinsa.subject.model.brand.BrandResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface BrandMapper {

    BrandResponse toResponseBrand(Brand source);

}
