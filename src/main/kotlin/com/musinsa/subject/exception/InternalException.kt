package com.musinsa.subject.exception

class InternalException(
    val exception: Exception
) : RuntimeException() {

    val type: InternalExceptionType = InternalExceptionType.entries
        .firstOrNull { it.exceptionClass == exception::class }
        ?: InternalExceptionType.DEFAULT

}
