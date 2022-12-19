package jpa.criteria;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Entity
class CityEntity {
    @Id
    private Long id;
    private String name;
    private Long population;
    @ManyToOne
    private RegionEntity region;

    public CityEntity() {
    }

    public CityEntity(Long id, String name, Long population, RegionEntity region) {
        this.id = id;
        this.name = name;
        this.population = population;
        this.region = region;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CityEntity that = (CityEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(population, that.population) && Objects.equals(region, that.region);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, population, region);
    }

    @Override
    public String toString() {
        return String.format("Город[id=%d, name=%s, region=%s]", id, name, region);
    }
}
