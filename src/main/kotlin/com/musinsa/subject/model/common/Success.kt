package com.musinsa.subject.model.common

open class Success : Result {
    constructor() : super(0, "성공")
    constructor(message: String) : super(0, message)
}
