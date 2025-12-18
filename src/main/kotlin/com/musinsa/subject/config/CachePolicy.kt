package com.musinsa.subject.config

import java.time.Duration

enum class CachePolicy(
    val cacheName: String,
    val maximumSize: Long,
    val expireAfterWrite: Duration
) {

    LOWEST_PRICE_BY_CATEGORY(CacheName.LOWEST_PRICE_BY_CATEGORY, 1, Duration.ofMinutes(10)),
    LOWEST_TOTAL_PRICE_BRAND_WITH_CATEGORY_DETAILS(CacheName.LOWEST_TOTAL_PRICE_BRAND_WITH_CATEGORY_DETAILS, 1, Duration.ofMinutes(10)),
    MIN_MAX_PRICE_BRANDS_BY_CATEGORY_NAME(CacheName.MIN_MAX_PRICE_BRANDS_BY_CATEGORY_NAME, 8, Duration.ofMinutes(10))

}
