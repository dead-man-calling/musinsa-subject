package com.musinsa.subject.model.product

import io.swagger.v3.oas.annotations.media.Schema

class ProductResponse(
    @get:Schema(defaultValue = "0")
    var productId: Long? = null,

    @get:Schema(defaultValue = "0")
    var productPrice: Long? = null
)
