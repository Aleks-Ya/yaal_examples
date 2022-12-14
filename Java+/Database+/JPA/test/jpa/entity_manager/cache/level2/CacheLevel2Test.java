package jpa.entity_manager.cache.level2;

import org.junit.jupiter.api.Test;

import javax.persistence.SharedCacheMode;
import java.util.List;

import static jpa.JpaHelper.h2RowCount;
import static jpa.JpaHelper.withEntityManagerFactory;
import static org.assertj.core.api.Assertions.assertThat;

class CacheLevel2Test {

    @Test
    void test() {
        withEntityManagerFactory(emFactory -> {
            assertThat(h2RowCount(emFactory, "UserEntity")).isEqualTo(0);
            var expUser = new UserEntity(1L, "John");

            var em1 = emFactory.createEntityManager();
            var em2 = emFactory.createEntityManager();
            assertThat(em1).isNotEqualTo(em2);

            var cache = emFactory.getCache();

            var transaction = em1.getTransaction();
            transaction.begin();
            em1.persist(expUser);

            assertThat(cache.contains(UserEntity.class, expUser.getId())).isFalse();

            var actUser1 = em1.find(UserEntity.class, expUser.getId());
            assertThat(actUser1).isEqualTo(expUser);
            assertThat(actUser1).isSameAs(expUser);
            assertThat(em2.find(UserEntity.class, expUser.getId())).isNull();

            assertThat(h2RowCount(emFactory, "UserEntity")).isEqualTo(0);
            transaction.commit();
            assertThat(cache.contains(UserEntity.class, expUser.getId())).isTrue();
            assertThat(h2RowCount(emFactory, "UserEntity")).isEqualTo(1);

            var actUser2 = em2.find(UserEntity.class, expUser.getId());
            assertThat(actUser2).isEqualTo(expUser);
            assertThat(actUser2).isNotSameAs(expUser);

            assertThat(actUser1).isNotSameAs(actUser2);

            cache.evict(UserEntity.class, expUser.getId());
            assertThat(cache.contains(UserEntity.class, expUser.getId())).isFalse();
        }, List.of(UserEntity.class), configuration -> {
            configuration.setSharedCacheMode(SharedCacheMode.ENABLE_SELECTIVE);
            return configuration
                    .setProperty("hibernate.cache.use_second_level_cache", "true")
                    .setProperty("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.EhCacheRegionFactory");
        });
    }

}