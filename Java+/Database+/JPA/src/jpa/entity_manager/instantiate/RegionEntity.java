package jpa.entity_manager.instantiate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
class RegionEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

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
