package com.musinsa.subject.model.category

import jakarta.validation.constraints.NotNull

data class CategoryRequest(
    @field:NotNull
    val categoryName: String
)
