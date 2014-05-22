package entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class RegionEntity {
    @Id
    @GeneratedValue
    public Long id;

    public String name;

    public RegionEntity() {
    }

    public RegionEntity(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("Регион[id=%d, name=%s]", id, name);
    }
}
