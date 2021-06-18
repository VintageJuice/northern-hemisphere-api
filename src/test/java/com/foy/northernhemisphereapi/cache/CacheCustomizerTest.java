package com.foy.northernhemisphereapi.cache;

import com.foy.northernhemisphereapi.NorthernHemisphereApiConfiguration;
import com.foy.northernhemisphereapi.utils.ConfigConstants;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ContextConfiguration(classes = NorthernHemisphereApiConfiguration.class)
class CacheCustomizerTest {

    private final CacheCustomizer cacheCustomizer = new CacheCustomizer();
    private final ConcurrentMapCacheManager concurrentMapCacheManager = new ConcurrentMapCacheManager();

    @Test
    void customizeCache() {
        cacheCustomizer.customize(concurrentMapCacheManager);
        assertTrue(concurrentMapCacheManager.getCacheNames().stream().anyMatch(cache -> cache.equals(ConfigConstants.IP_CACHE_NAME)));
    }
}