package com.musinsa.subject.service.core;

import com.musinsa.subject.entity.Category;
import com.musinsa.subject.exception.DomainException;
import com.musinsa.subject.exception.DomainExceptionType;
import com.musinsa.subject.model.category.CategoryRequest;
import com.musinsa.subject.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;

    public Category getCategory(long categoryId) {
        return repository.findById(categoryId)
                .orElseThrow(() ->
                        DomainException.builder()
                                .type(DomainExceptionType.CATEGORY_NOT_FOUND)
                                .build()
                );
    }

    public Category getCategoryByCategoryName(String categoryName) {
        return repository.findFirstByCategoryName(categoryName)
                .orElseThrow(() ->
                        DomainException.builder()
                                .type(DomainExceptionType.CATEGORY_NOT_FOUND)
                                .build()
                );
    }

    public Category createCategory(CategoryRequest request) {
        return repository.save(Category.builder().categoryName(request.getCategoryName()).build());
    }

    public Category updateCategory(long categoryId, CategoryRequest request) {
        Category category = this.getCategory(categoryId);

        category.setCategoryName(request.getCategoryName());

        return repository.save(category);
    }

    public void deleteCategory(long categoryId) {
        repository.deleteById(categoryId);
    }
    
}
