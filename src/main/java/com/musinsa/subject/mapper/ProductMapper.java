package com.musinsa.subject.mapper;

import com.musinsa.subject.entity.Product;
import com.musinsa.subject.model.product.ProductResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ProductMapper {

    ProductResponse toResponseProduct(Product source);

}
