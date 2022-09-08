package jpa.jpql;

import java.util.List;

public class MealEntities {
    public static final UserEntity user = new UserEntity(1L, "Smith");
    public static final MealEntity meal1 = new MealEntity(1L, "Sandwich", MealCategory.FAST_FOOD, user);
    public static final MealEntity meal2 = new MealEntity(2L, "Soup", MealCategory.SOUPS, user);
    public static final MealEntity meal3 = new MealEntity(3L, "Salad", MealCategory.VEGETABLES, user);
    public static final List<?> entities = List.of(user, meal1, meal2, meal3);
    public static final List<Class<?>> classes = List.of(UserEntity.class, MealEntity.class);
}
