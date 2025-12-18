package com.musinsa.subject.config

import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.caffeine.CaffeineCache
import org.springframework.cache.support.SimpleCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableCaching
class CacheConfig {

    @Bean
    fun cacheManager(): CacheManager {
        val cacheManager = SimpleCacheManager()

        cacheManager.setCaches(
            CachePolicy.entries.map { c ->
                CaffeineCache(
                    c.cacheName,
                    Caffeine.newBuilder()
                        .maximumSize(c.maximumSize)
                        .expireAfterWrite(c.expireAfterWrite)
                        .recordStats()
                        .build()
                )
            }
        )

        return cacheManager
    }

}
