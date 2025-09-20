package com.mohan.class_com.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.CacheManager;
import javax.cache.Caching;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public org.springframework.cache.CacheManager cacheManager() {
        CacheManager cacheManager = Caching.getCachingProvider().getCacheManager();

        // Register heap-only caches
        cacheManager.createCache("product", EhcacheConfigs.productCacheConfig());
        cacheManager.createCache("products", EhcacheConfigs.productsCacheConfig());

        return new JCacheCacheManager(cacheManager);
    }
}
