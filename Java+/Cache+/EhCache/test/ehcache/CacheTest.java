package ehcache;

import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CacheTest {
    @Test
    void createCache() {
        try (var cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build(true)) {
            var myCache = cacheManager.createCache("myCache",
                    CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class,
                            ResourcePoolsBuilder.heap(100)).build());
            var expValue = "da one!";
            myCache.put(1L, expValue);
            var actValue = myCache.get(1L);
            assertThat(actValue).isEqualTo(expValue);
        }
    }

    @Test
    void preConfiguredCache() {
        try (var cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .withCache("preConfigured",
                        CacheConfigurationBuilder
                                .newCacheConfigurationBuilder(Long.class, String.class, ResourcePoolsBuilder.heap(100))
                                .build())
                .build(true)) {

            var preConfigured = cacheManager.getCache("preConfigured", Long.class, String.class);

            var expValue = "da one!";
            preConfigured.put(1L, expValue);
            var actValue = preConfigured.get(1L);
            assertThat(actValue).isEqualTo(expValue);
        }
    }
}