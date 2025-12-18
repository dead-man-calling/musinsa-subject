package com.musinsa.subject.service.core

import com.musinsa.subject.entity.Category
import com.musinsa.subject.exception.DomainException
import com.musinsa.subject.exception.DomainExceptionType
import com.musinsa.subject.model.category.CategoryRequest
import com.musinsa.subject.repository.CategoryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CategoryService(
    private val repository: CategoryRepository
) {

    fun getCategory(categoryId: Long): Category {
        return repository.findById(categoryId)
            .orElseThrow {
                DomainException(type = DomainExceptionType.CATEGORY_NOT_FOUND)
            }
    }

    fun getCategoryByCategoryName(categoryName: String): Category {
        return repository.findFirstByCategoryName(categoryName)
            .orElseThrow {
                DomainException(type = DomainExceptionType.CATEGORY_NOT_FOUND)
            }
    }

    fun createCategory(request: CategoryRequest): Category {
        return repository.save(Category(categoryName = request.categoryName))
    }

    @Transactional
    fun updateCategory(categoryId: Long, request: CategoryRequest): Category {
        val category = getCategory(categoryId)
        category.categoryName = request.categoryName
        return repository.save(category)
    }

    fun deleteCategory(categoryId: Long) {
        repository.deleteById(categoryId)
    }
}
