package com.mohan.class_com.config;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import javax.cache.configuration.Configuration;
import java.time.Duration;

public class EhcacheConfigs {

    public static Configuration<Object, Object> productCacheConfig() {
        return Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder.newCacheConfigurationBuilder(
                                Object.class, Object.class,
                                ResourcePoolsBuilder.heap(500) // 500 entries in heap
                        )
                        .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofMinutes(30)))
                        .build()
        );
    }

    public static Configuration<Object, Object> productsCacheConfig() {
        return Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder.newCacheConfigurationBuilder(
                                Object.class, Object.class,
                                ResourcePoolsBuilder.heap(1000) // 1000 entries in heap
                        )
                        .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofMinutes(10)))
                        .build()
        );
    }
}
