package com.musinsa.subject.model.brand

import jakarta.validation.constraints.NotNull

data class BrandRequest(
    @field:NotNull
    val brandName: String
)
