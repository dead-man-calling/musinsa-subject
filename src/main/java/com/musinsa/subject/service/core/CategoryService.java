package com.musinsa.subject.service.core;

import com.musinsa.subject.entity.Category;
import com.musinsa.subject.model.category.CategoryRequest;
import com.musinsa.subject.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;

    public Category getCategory(long categoryId) {
        return repository.findById(categoryId).orElseThrow();
    }

    public Category getCategoryByCategoryName(String categoryName) {
        return repository.findFirstByCategoryName(categoryName).orElseThrow();
    }

    public Category createCategory(CategoryRequest request) {
        return repository.save(Category.builder().categoryName(request.getCategoryName()).build());
    }

    public Category updateCategory(long categoryId, CategoryRequest request) {
        Category category = repository.findById(categoryId).orElseThrow();

        category.setCategoryName(request.getCategoryName());

        return repository.save(category);
    }

    public void deleteCategory(long categoryId) {
        repository.deleteById(categoryId);
    }
    
}
