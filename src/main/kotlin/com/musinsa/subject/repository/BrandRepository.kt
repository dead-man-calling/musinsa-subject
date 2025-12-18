package com.musinsa.subject.repository

import com.musinsa.subject.entity.Brand
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BrandRepository : JpaRepository<Brand, Long> {
    fun findAllByOrderByBrandId(): List<Brand>
}
