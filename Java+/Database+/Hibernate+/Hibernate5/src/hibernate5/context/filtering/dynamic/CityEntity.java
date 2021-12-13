package hibernate5.context.filtering.dynamic;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static hibernate5.context.filtering.dynamic.CityEntity.CITY_POPULATION_FILTER;
import static hibernate5.context.filtering.dynamic.CityEntity.MIN_POPULATION_PARAM;

@Entity
@FilterDef(name = CITY_POPULATION_FILTER,
        parameters = @ParamDef(name = MIN_POPULATION_PARAM, type = "org.hibernate.type.IntegerType"))
@Filter(name = CITY_POPULATION_FILTER, condition = "population > :" + MIN_POPULATION_PARAM)
class CityEntity {
    public static final String CITY_POPULATION_FILTER = "cityPopulationFilter";
    public static final String MIN_POPULATION_PARAM = "minPopulation";
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Integer population;

    public CityEntity() {
    }

    public CityEntity(String name, Integer population) {
        this.name = name;
        this.population = population;
    }

    @Override
    public String toString() {
        return String.format("City[id=%d, name=%s, population=%dk]", id, name, population / 1000);
    }
}
