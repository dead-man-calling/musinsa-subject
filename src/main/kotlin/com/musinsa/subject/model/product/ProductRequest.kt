package com.musinsa.subject.model.product

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

data class ProductRequest(
    @get:Schema(defaultValue = "0")
    @field:NotNull
    @field:Positive
    val productPrice: Long,

    @get:Schema(defaultValue = "0")
    @field:NotNull
    val brandId: Long,

    @get:Schema(defaultValue = "0")
    @field:NotNull
    val categoryId: Long
)
