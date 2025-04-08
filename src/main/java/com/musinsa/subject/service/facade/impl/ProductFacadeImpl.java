package com.musinsa.subject.service.facade.impl;

import com.musinsa.subject.entity.Brand;
import com.musinsa.subject.entity.Category;
import com.musinsa.subject.entity.Product;
import com.musinsa.subject.mapper.ProductMapper;
import com.musinsa.subject.model.product.ProductRequest;
import com.musinsa.subject.model.product.ProductResponse;
import com.musinsa.subject.service.core.BrandService;
import com.musinsa.subject.service.core.CategoryService;
import com.musinsa.subject.service.core.ProductService;
import com.musinsa.subject.service.facade.ProductFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductFacadeImpl implements ProductFacade {

    private final BrandService brandService;
    private final CategoryService categoryService;
    private final ProductService productService;
    private final ProductMapper productMapper;

    public ProductResponse createProduct(ProductRequest request) {
        Brand brand = brandService.getBrand(request.getBrandId());
        Category category = categoryService.getCategory(request.getCategoryId());

        Product product = productService.createProduct(request.getProductPrice(), brand, category);

        return productMapper.toResponseProduct(product);
    }

    public ProductResponse getProduct(long productId) {
        return productMapper.toResponseProduct(productService.getProduct(productId));
    }

    public ProductResponse updateProduct(long productId, ProductRequest request) {
        return productMapper.toResponseProduct(productService.updateProduct(productId, request));
    }

    public void deleteProduct(long productId) {
        productService.deleteProduct(productId);
    }

}
