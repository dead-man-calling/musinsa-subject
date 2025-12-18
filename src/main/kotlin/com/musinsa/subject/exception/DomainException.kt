package com.musinsa.subject.exception

class DomainException(
    val type: DomainExceptionType,
    val data: Any? = null,
    val reason: String? = null
) : RuntimeException()
