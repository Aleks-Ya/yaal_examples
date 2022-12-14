package jcache;

import org.junit.jupiter.api.Test;

import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

class CacheTest {
    @Test
    void createCache() {
        var config = new MutableConfiguration<String, String>();
        try (var cachingProvider = Caching.getCachingProvider();
             var cacheManager = cachingProvider.getCacheManager();
             var cache = cacheManager.createCache("simpleCache", config)) {
            var expValue = "value1";
            cache.put("key1", expValue);
            var actValue = cache.get("key1");
            assertThat(actValue).isEqualTo(expValue);
        }
    }
}