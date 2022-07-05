package hibernate5.criteria.association;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
class CityEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Long population;

    @ManyToOne
    private RegionEntity region;

    public CityEntity() {
    }

    public CityEntity(String name, Long population, RegionEntity region) {
        this.name = name;
        this.population = population;
        this.region = region;
    }

    @Override
    public String toString() {
        return String.format("Город[id=%d, name=%s, population=%dтыс region=%s]", id, name, population / 1000, region);
    }
}
