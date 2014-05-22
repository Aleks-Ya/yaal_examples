package entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class CityEntity {
    @Id
    @GeneratedValue
    public Long id;

    public String name;

    @ManyToOne
    public RegionEntity region;

    public CityEntity() {
    }

    public CityEntity(String name, RegionEntity region) {
        this.name = name;
        this.region = region;
    }

    @Override
    public String toString() {
        return String.format("Город[id=%d, name=%name, region=%region]", id, name, region);
    }
}
