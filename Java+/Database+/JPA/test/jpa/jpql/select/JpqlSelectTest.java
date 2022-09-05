package jpa.jpql.select;

import jpa.jpql.MealEntities;
import jpa.jpql.MealEntity;
import jpa.jpql.UserEntity;
import org.junit.jupiter.api.Test;

import static jpa.JpaHelper.withEntityManagerAndSavedEntities;
import static org.assertj.core.api.Assertions.assertThat;

class JpqlSelectTest {

    @Test
    void selectAll() {
        withEntityManagerAndSavedEntities((em) -> {
            var query = em.createQuery("SELECT m FROM MealEntity m", MealEntity.class);
            var meals = query.getResultList();
            assertThat(meals).contains(MealEntities.meal1, MealEntities.meal2, MealEntities.meal3);
        }, MealEntities.entities);
    }

    @Test
    void selectAllShort() {
        withEntityManagerAndSavedEntities((em) -> {
            var query = em.createQuery("FROM UserEntity", UserEntity.class);
            var users = query.getResultList();
            assertThat(users).contains(MealEntities.user);
        }, MealEntities.entities);
    }

    @Test
    void selectWhereShort() {
        withEntityManagerAndSavedEntities((em) -> {
            var query = em.createQuery(
                    "FROM MealEntity WHERE name LIKE 'Sandwich'", MealEntity.class);
            var meal = query.getSingleResult();
            assertThat(meal).isEqualTo(MealEntities.meal1);
        }, MealEntities.entities);
    }

    @Test
    void joinByPath() {
        withEntityManagerAndSavedEntities((em) -> {
            var query = em.createQuery(
                    "FROM MealEntity WHERE user.name LIKE 'Smith'", MealEntity.class);
            var meals = query.getResultList();
            assertThat(meals)
                    .contains(MealEntities.meal1, MealEntities.meal2, MealEntities.meal3)
                    .map(MealEntity::getUser).allSatisfy(user -> assertThat(user).isEqualTo(MealEntities.user));
        }, MealEntities.entities);
    }

    @Test
    void joinByJoin() {
        withEntityManagerAndSavedEntities((em) -> {
            var query = em.createQuery(
                    "SELECT m FROM MealEntity m JOIN m.user u WHERE u.name LIKE 'Smith'", MealEntity.class);
            var meals = query.getResultList();
            assertThat(meals)
                    .contains(MealEntities.meal1, MealEntities.meal2, MealEntities.meal3)
                    .map(MealEntity::getUser).allSatisfy(user -> assertThat(user).isEqualTo(MealEntities.user));
        }, MealEntities.entities);
    }

    @Test
    void selectWhereEnum() {
        withEntityManagerAndSavedEntities((em) -> {
            var query = em.createQuery(
                    "FROM MealEntity WHERE category = jpa.jpql.MealCategory.VEGETABLES", MealEntity.class);
            var meal = query.getSingleResult();
            assertThat(meal).isEqualTo(MealEntities.meal3);
        }, MealEntities.entities);
    }

    @Test
    void selectWhereParameter() {
        withEntityManagerAndSavedEntities((em) -> {
            var query = em.createQuery(
                    "FROM MealEntity WHERE name LIKE :meal", MealEntity.class);
            query.setParameter("meal", "Sandwich");
            var meal = query.getSingleResult();
            assertThat(meal).isEqualTo(MealEntities.meal1);
        }, MealEntities.entities);
    }
}