package com.musinsa.subject.service.facade;

import com.musinsa.subject.model.product.ProductRequest;
import com.musinsa.subject.model.product.ProductResponse;

public interface ProductFacade {

    ProductResponse createProduct(ProductRequest request);

    ProductResponse getProduct(long productId);

    ProductResponse updateProduct(long productId, ProductRequest request);

    void deleteProduct(long productId);
    
}
