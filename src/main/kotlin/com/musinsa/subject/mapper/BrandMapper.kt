package com.musinsa.subject.mapper

import com.musinsa.subject.entity.Brand
import com.musinsa.subject.model.brand.BrandResponse
import io.swagger.v3.oas.annotations.Hidden
import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy

@Hidden
@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
interface BrandMapper {
    fun toResponseBrand(source: Brand): BrandResponse
}
