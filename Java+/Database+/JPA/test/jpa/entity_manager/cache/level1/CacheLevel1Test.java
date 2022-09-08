package jpa.entity_manager.cache.level1;

import jpa.jpql.MealEntities;
import jpa.jpql.UserEntity;
import org.junit.jupiter.api.Test;

import static jpa.JpaHelper.withEntityManagerFactory;
import static org.assertj.core.api.Assertions.assertThat;

class CacheLevel1Test {

    @Test
    void test() {
        withEntityManagerFactory((emFactory) -> {
            var expUser = MealEntities.user;

            var em1 = emFactory.createEntityManager();
            var em2 = emFactory.createEntityManager();
            assertThat(em1).isNotEqualTo(em2);

            var transaction = em1.getTransaction();
            transaction.begin();
            em1.persist(expUser);

            var actUser1 = em1.find(UserEntity.class, expUser.getId());
            assertThat(actUser1).isEqualTo(expUser);
            assertThat(actUser1).isSameAs(expUser);
            assertThat(em2.find(UserEntity.class, expUser.getId())).isNull();

            transaction.commit();

            var actUser2 = em2.find(UserEntity.class, expUser.getId());
            assertThat(actUser2).isEqualTo(expUser);
            assertThat(actUser2).isNotSameAs(expUser);

            assertThat(actUser1).isNotSameAs(actUser2);
        }, MealEntities.classes);
    }

}