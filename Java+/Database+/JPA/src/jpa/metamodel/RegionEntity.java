package jpa.metamodel;

import javax.persistence.Entity;

@Entity
public class RegionEntity extends BaseEntity {

    public RegionEntity() {
    }

    public RegionEntity(Long id, String name) {
        super(id, name);
    }
}
