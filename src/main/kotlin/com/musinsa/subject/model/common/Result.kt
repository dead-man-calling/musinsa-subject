package com.musinsa.subject.model.common

import io.swagger.v3.oas.annotations.media.Schema

open class Result(
    @get:Schema(defaultValue = "0")
    val code: Int,
    val message: String
)
