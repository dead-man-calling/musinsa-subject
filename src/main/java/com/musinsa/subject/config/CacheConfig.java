package com.musinsa.subject.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.stream.Collectors;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();

        cacheManager.setCaches(
                Arrays.stream(CachePolicy.values())
                        .map(c ->
                                new CaffeineCache(
                                        c.getCacheName(),
                                        Caffeine.newBuilder()
                                                .maximumSize(c.getMaximumSize())
                                                .expireAfterWrite(c.getExpireAfterWrite())
                                                .recordStats()
                                                .build()
                                )
                        )
                        .collect(Collectors.toList())
        );

        return cacheManager;
    }

}