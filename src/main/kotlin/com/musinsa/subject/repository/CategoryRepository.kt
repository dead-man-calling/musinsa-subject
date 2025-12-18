package com.musinsa.subject.repository

import com.musinsa.subject.entity.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CategoryRepository : JpaRepository<Category, Long> {
    fun findAllByOrderByCategoryId(): List<Category>
    fun findFirstByCategoryName(categoryName: String): Optional<Category>
}
