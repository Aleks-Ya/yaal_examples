package metamodel;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Entity
public class CityEntity extends BaseEntity {
    private Long population;
    @ManyToOne
    private RegionEntity region;

    @Embedded
    private Mayor mayor;

    public CityEntity() {
    }

    public CityEntity(Long id, String name, Long population, RegionEntity region, Mayor mayor) {
        super(id, name);
        this.population = population;
        this.region = region;
        this.mayor = mayor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CityEntity that = (CityEntity) o;
        return Objects.equals(population, that.population) && Objects.equals(region, that.region) && Objects.equals(mayor, that.mayor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), population, region, mayor);
    }
}
