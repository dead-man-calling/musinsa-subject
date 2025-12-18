package com.musinsa.subject.model.brand

import io.swagger.v3.oas.annotations.media.Schema

class BrandResponse(
    @get:Schema(defaultValue = "0")
    var brandId: Long? = null,
    var brandName: String? = null
)
