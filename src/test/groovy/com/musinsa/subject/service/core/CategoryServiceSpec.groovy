package com.musinsa.subject.service.core

import com.musinsa.subject.entity.Category
import com.musinsa.subject.exception.DomainException
import com.musinsa.subject.model.category.CategoryRequest
import com.musinsa.subject.repository.CategoryRepository
import spock.lang.Specification

class CategoryServiceSpec extends Specification {

    CategoryRepository repository = Mock(CategoryRepository)
    CategoryService categoryService = new CategoryService(repository)

    def "카테고리 조회 - 존재하는 카테고리 ID로 조회 시 해당 카테고리를 반환한다"() {
        given:
        def categoryId = 1L
        def categoryName = "상의"
        def category = Category.builder()
                .categoryId(categoryId)
                .categoryName(categoryName)
                .build()

        and:
        1 * repository.findById(categoryId) >> Optional.of(category)

        when:
        def result = categoryService.getCategory(categoryId)

        then:
        result.categoryId == categoryId
        result.categoryName == categoryName
    }

    def "카테고리 조회 - 존재하지 않는 카테고리 ID로 조회 시 예외가 발생한다"() {
        given:
        def categoryId = 999L

        and:
        1 * repository.findById(categoryId) >> Optional.empty()

        when:
        categoryService.getCategory(categoryId)

        then:
        thrown(DomainException)
    }

    def "카테고리 조회 - 존재하는 카테고리 이름으로 조회 시 해당 카테고리를 반환한다"() {
        given:
        def categoryId = 1L
        def categoryName = "상의"
        def category = Category.builder()
                .categoryId(categoryId)
                .categoryName(categoryName)
                .build()

        and:
        1 * repository.findFirstByCategoryName(categoryName) >> Optional.of(category)

        when:
        def result = categoryService.getCategoryByCategoryName(categoryName)

        then:
        result.categoryId == categoryId
        result.categoryName == categoryName
    }

    def "카테고리 조회 - 존재하지 않는 카테고리 이름으로 조회 시 예외가 발생한다"() {
        given:
        def categoryName = "가전제품"

        and:
        1 * repository.findFirstByCategoryName(categoryName) >> Optional.empty()

        when:
        categoryService.getCategoryByCategoryName(categoryName)

        then:
        thrown(DomainException)
    }

    def "카테고리 생성 - 유효한 요청으로 카테고리를 생성한다"() {
        given:
        def categoryId = 1L
        def categoryName = "상의"

        and:
        def request = new CategoryRequest()
        request.setCategoryName(categoryName)

        and:
        def savedCategory = Category.builder()
                .categoryId(categoryId)
                .categoryName(categoryName)
                .build()

        and:
        1 * repository.save(_ as Category) >> savedCategory

        when:
        def result = categoryService.createCategory(request)

        then:
        result.categoryId == categoryId
        result.categoryName == categoryName
    }

    def "카테고리 수정 - 존재하는 카테고리의 이름을 수정한다"() {
        given:
        def categoryId = 1L
        def oldCategoryName = "상의"
        def newCategoryName = "바지"

        and:
        def request = new CategoryRequest()
        request.setCategoryName(newCategoryName)

        and:
        def existingCategory = Category.builder()
                .categoryId(categoryId)
                .categoryName(oldCategoryName)
                .build()
        def updatedCategory = Category.builder()
                .categoryId(categoryId)
                .categoryName(newCategoryName)
                .build()

        and:
        1 * repository.findById(categoryId) >> Optional.of(existingCategory)
        1 * repository.save(_ as Category) >> updatedCategory

        when:
        def result = categoryService.updateCategory(categoryId, request)

        then:
        result.categoryId == categoryId
        result.categoryName == newCategoryName
    }

    def "카테고리 수정 - 존재하지 않는 카테고리 ID로 수정 시 예외가 발생한다"() {
        given:
        def categoryId = 999L
        def categoryName = "A"

        and:
        def request = new CategoryRequest()
        request.setCategoryName(categoryName)

        and:
        1 * repository.findById(categoryId) >> Optional.empty()
        0 * repository.save(_)

        when:
        categoryService.updateCategory(categoryId, request)

        then:
        thrown(DomainException)
    }

    def "카테고리 삭제 - 카테고리 ID로 삭제를 요청한다"() {
        given:
        def categoryId = 1L

        when:
        categoryService.deleteCategory(categoryId)

        then:
        1 * repository.deleteById(categoryId)
    }

}
