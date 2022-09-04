package jpa.criteria;

import java.util.List;

public class RegionEntities {
    public static final RegionEntity region1 = new RegionEntity(1L, "Вологодская область");
    public static final CityEntity city1 = new CityEntity(1L, "Вологда", 300000L, region1);
    public static final CityEntity city2 = new CityEntity(2L, "Санкт-Петербург", 4000000L, region1);
    public static final CityEntity city3 = new CityEntity(3L, "Волгоград", 1000000L, region1);
    public static final RegionEntity region2 = new RegionEntity(2L, "Московская область");
    public static final CityEntity city4 = new CityEntity(4L, "Москва", 12000000L, region2);

    public static final List<?> entities = List.of(region1, city1, city2, city3, region2, city4);
    public static final List<Class<?>> entityClasses = List.of(RegionEntity.class, CityEntity.class);
}
