package com.musinsa.subject.model.common

import com.fasterxml.jackson.annotation.JsonInclude

class Fail : Result {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val reason: String?

    constructor(message: String) : super(-1, message) {
        this.reason = null
    }

    constructor(message: String, reason: String) : super(-1, message) {
        this.reason = reason
    }

    constructor(code: Int, message: String, reason: String?) : super(code, message) {
        this.reason = reason
    }
}
