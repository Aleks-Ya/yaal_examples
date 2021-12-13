package hibernate5.context.filtering.statik;

import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Where( clause = "population > 1000000" )
class CityEntity {
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
        return String.format("City[id=%d, name=%s, population=%dk]", id, name, population/1000);
    }
}
