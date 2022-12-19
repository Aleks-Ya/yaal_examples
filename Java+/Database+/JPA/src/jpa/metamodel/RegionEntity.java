package jpa.metamodel;

import javax.persistence.Entity;

@Entity
class RegionEntity extends BaseEntity {

    public RegionEntity() {
    }

    public RegionEntity(Long id, String name) {
        super(id, name);
    }
}
