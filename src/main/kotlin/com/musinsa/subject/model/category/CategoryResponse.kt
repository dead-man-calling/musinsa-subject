package com.musinsa.subject.model.category

import io.swagger.v3.oas.annotations.media.Schema

class CategoryResponse(
    @get:Schema(defaultValue = "0")
    var categoryId: Long? = null,
    var categoryName: String? = null
)
