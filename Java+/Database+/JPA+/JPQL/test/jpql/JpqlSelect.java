package jpql;

import jpql.Helper;
import jpql.MealEntity;
import jpql.UserEntity;
import org.junit.jupiter.api.Test;

import static jpql.Helper.initEntityManagerFactory;
import static jpql.Helper.saveEntities;
import static org.assertj.core.api.Assertions.assertThat;

class JpqlSelect {

    @Test
    void selectAll() {
        try (var emFactory = initEntityManagerFactory()) {
            var em = emFactory.createEntityManager();
            saveEntities(em);
            var query = em.createQuery("SELECT m FROM MealEntity m", MealEntity.class);
            var meals = query.getResultList();
            assertThat(meals).contains(Helper.meal1, Helper.meal2, Helper.meal3);
        }
    }

    @Test
    void selectAllShort() {
        try (var emFactory = initEntityManagerFactory()) {
            var em = emFactory.createEntityManager();
            saveEntities(em);
            var query = em.createQuery("FROM UserEntity", UserEntity.class);
            var users = query.getResultList();
            assertThat(users).contains(Helper.user);
        }
    }

    @Test
    void fromWhere() {
        try (var emFactory = initEntityManagerFactory()) {
            var em = emFactory.createEntityManager();
            saveEntities(em);
            var query = em.createQuery("FROM MealEntity WHERE name LIKE 'Sandwich'", MealEntity.class);
            var meal = query.getSingleResult();
            assertThat(meal).isEqualTo(Helper.meal1);
        }
    }

    @Test
    void joinByPath() {
        try (var emFactory = initEntityManagerFactory()) {
            var em = emFactory.createEntityManager();
            saveEntities(em);
            var query = em.createQuery("FROM MealEntity WHERE user.name LIKE 'Smith'", MealEntity.class);
            var meals = query.getResultList();
            assertThat(meals)
                    .contains(Helper.meal1, Helper.meal2, Helper.meal3)
                    .map(MealEntity::getUser).allSatisfy(user -> assertThat(user).isEqualTo(Helper.user));
        }
    }

    @Test
    void joinByJoin() {
        try (var emFactory = initEntityManagerFactory()) {
            var em = emFactory.createEntityManager();
            saveEntities(em);
            var query = em.createQuery("SELECT m FROM MealEntity m JOIN m.user u WHERE u.name LIKE 'Smith'", MealEntity.class);
            var meals = query.getResultList();
            assertThat(meals)
                    .contains(Helper.meal1, Helper.meal2, Helper.meal3)
                    .map(MealEntity::getUser).allSatisfy(user -> assertThat(user).isEqualTo(Helper.user));
        }
    }
}