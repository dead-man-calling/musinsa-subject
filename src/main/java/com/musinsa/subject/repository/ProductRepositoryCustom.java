package com.musinsa.subject.repository;

import com.musinsa.subject.entity.Product;

import java.util.List;

public interface ProductRepositoryCustom {

    List<Product> getLowestPriceProductByCategory();

    List<Product> getLowestPriceProductByBrandAndCategory();

    List<Product> getMinPriceProductInCategory(long categoryId);

    List<Product> getMaxPriceProductInCategory(long categoryId);

}
