package com.musinsa.subject.repository;

import com.musinsa.subject.entity.Product;
import com.musinsa.subject.entity.QProduct;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.AbstractMap;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory factory;

    public List<Product> getLowestPriceProductByCategory() {
        QProduct product = QProduct.product;
        QProduct subProduct = new QProduct("subProduct");

        return factory.selectFrom(product)
                .leftJoin(product.brand).fetchJoin()
                .leftJoin(product.category).fetchJoin()
                .where(
                        JPAExpressions
                                .select(subProduct.productPrice.min())
                                .from(subProduct)
                                .where(subProduct.category.categoryId.eq(product.category.categoryId))
                                .eq(product.productPrice)
                )
                .fetch()
                .stream()
                .collect(Collectors.toMap(
                        p -> p.getCategory().getCategoryId(),
                        p -> p,
                        (existing, replacement) -> replacement
                ))
                .values()
                .stream()
                .toList();
    }

    public List<Product> getLowestPriceProductByBrandAndCategory() {
        QProduct product = QProduct.product;
        QProduct subProduct = new QProduct("subProduct");

        return factory.selectFrom(product)
                .leftJoin(product.brand).fetchJoin()
                .leftJoin(product.category).fetchJoin()
                .where(
                        JPAExpressions
                                .select(subProduct.productPrice.min())
                                .from(subProduct)
                                .where(subProduct.brand.brandId.eq(product.brand.brandId))
                                .where(subProduct.category.categoryId.eq(product.category.categoryId))
                                .eq(product.productPrice)
                )
                .fetch()
                .stream()
                .collect(Collectors.toMap(
                        p -> new AbstractMap.SimpleEntry<>(p.getBrand().getBrandId(), p.getCategory().getCategoryId()),
                        p -> p,
                        (existing, replacement) -> replacement
                ))
                .values()
                .stream()
                .toList();
    }

    public List<Product> getMinPriceProductInCategory(long categoryId) {
        QProduct product = QProduct.product;
        QProduct subProduct = new QProduct("subProduct");

        return factory.selectFrom(product)
                .leftJoin(product.brand).fetchJoin()
                .leftJoin(product.category).fetchJoin()
                .where(
                        JPAExpressions
                                .select(subProduct.productPrice.min())
                                .from(subProduct)
                                .where(subProduct.category.categoryId.eq(categoryId))
                                .eq(product.productPrice)
                )
                .fetch();
    }

    public List<Product> getMaxPriceProductInCategory(long categoryId) {
        QProduct product = QProduct.product;
        QProduct subProduct = new QProduct("subProduct");

        return factory.selectFrom(product)
                .leftJoin(product.brand).fetchJoin()
                .leftJoin(product.category).fetchJoin()
                .where(
                        JPAExpressions
                                .select(subProduct.productPrice.max())
                                .from(subProduct)
                                .where(subProduct.category.categoryId.eq(categoryId))
                                .eq(product.productPrice)
                )
                .fetch();
    }

}
