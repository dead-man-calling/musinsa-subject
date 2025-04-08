package com.musinsa.subject.config;

import com.musinsa.subject.entity.Brand;
import com.musinsa.subject.entity.Category;
import com.musinsa.subject.entity.Product;
import com.musinsa.subject.repository.BrandRepository;
import com.musinsa.subject.repository.CategoryRepository;
import com.musinsa.subject.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Profile("local")
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final String[] CATEGORY_NAMES = {
            "상의",
            "아우터",
            "바지",
            "스니커즈",
            "가방",
            "모자",
            "양말",
            "액세서리"
    };

    private final String[] BRAND_NAMES = {
            "A",
            "B",
            "C",
            "D",
            "E",
            "F",
            "G",
            "H",
            "I"
    };

    private final Long[][] PRODUCT_PRICES = {
            // 상의    아우터    바지   스니커즈  가방    모자    양말   액세서리
            {11200L, 5500L, 4200L, 9000L, 2000L, 1700L, 1800L, 2300L}, // A
            {10500L, 5900L, 3800L, 9100L, 2100L, 2000L, 2000L, 2200L}, // B
            {10000L, 6200L, 3300L, 9200L, 2200L, 1900L, 2200L, 2100L}, // C
            {10100L, 5100L, 3000L, 9500L, 2500L, 1500L, 2400L, 2000L}, // D
            {10700L, 5000L, 3800L, 9900L, 2300L, 1800L, 2100L, 2100L}, // E
            {11200L, 7200L, 4000L, 9300L, 2100L, 1600L, 2300L, 1900L}, // F
            {10500L, 5800L, 3900L, 9000L, 2200L, 1700L, 2100L, 2000L}, // G
            {10800L, 6300L, 3100L, 9700L, 2100L, 1600L, 2000L, 2000L}, // H
            {11400L, 6700L, 3200L, 9500L, 2400L, 1700L, 1700L, 2400L}, // I
    };

    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final ProductRepository productRepository;

    private void createCategories() {
        List<Category> categories = new ArrayList<>();

        for (String categoryName : CATEGORY_NAMES)
            categories.add(Category.builder().categoryName(categoryName).build());

        categoryRepository.saveAll(categories);
    }

    private void createBrands() {
        List<Brand> brands = new ArrayList<>();

        for (String brandName : BRAND_NAMES)
            brandRepository.save(Brand.builder().brandName(brandName).build());

        brandRepository.saveAll(brands);
    }

    private void createProducts() {
        List<Brand> brands = brandRepository.findAllByOrderByBrandId();
        List<Category> categories = categoryRepository.findAllByOrderByCategoryId();

        List<Product> products = new ArrayList<>();

        for (int i = 0; i < brands.size(); i++) {
            Brand brand = brands.get(i);

            for (int j = 0; j < categories.size(); j++) {
                Category category = categories.get(j);

                Long productPrice = PRODUCT_PRICES[i][j];

                Product product = Product.builder()
                        .productPrice(productPrice)
                        .brand(brand)
                        .category(category)
                        .build();

                products.add(product);
            }

        }

        productRepository.saveAll(products);
    }

    @Override
    public void run(String... args) {
        this.createCategories();
        this.createBrands();
        this.createProducts();
    }

}