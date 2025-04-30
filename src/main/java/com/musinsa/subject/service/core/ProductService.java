package com.musinsa.subject.service.core;

import com.musinsa.subject.entity.Brand;
import com.musinsa.subject.entity.Category;
import com.musinsa.subject.entity.Product;
import com.musinsa.subject.exception.DomainException;
import com.musinsa.subject.exception.DomainExceptionType;
import com.musinsa.subject.model.product.ProductRequest;
import com.musinsa.subject.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    public Product getProduct(long productId) {
        return repository.findById(productId)
                .orElseThrow(() ->
                        DomainException.builder()
                                .type(DomainExceptionType.PRODUCT_NOT_FOUND)
                                .build()
                );
    }

    public Product createProduct(long productPrice, Brand brand, Category category) {
        return repository.save(
                Product.builder()
                        .productPrice(productPrice)
                        .brand(brand)
                        .category(category)
                        .build()
        );
    }

    public Product updateProduct(long productId, ProductRequest request) {
        Product product = this.getProduct(productId);

        product.setProductPrice(request.getProductPrice());

        return repository.save(product);
    }

    public void deleteProduct(long productId) {
        repository.deleteById(productId);
    }

    public List<Product> getLowestPriceProductByCategory() {
        return repository.getLowestPriceProductByCategory();
    }

    public List<Product> getLowestPriceProductByBrandAndCategory() {
        return repository.getLowestPriceProductByBrandAndCategory();
    }

    public List<Product> getMinPriceProductInCategory(long categoryId) {
        return repository.getMinPriceProductInCategory(categoryId);
    }

    public List<Product> getMaxPriceProductInCategory(long categoryId) {
        return repository.getMaxPriceProductInCategory(categoryId);
    }
    
}
