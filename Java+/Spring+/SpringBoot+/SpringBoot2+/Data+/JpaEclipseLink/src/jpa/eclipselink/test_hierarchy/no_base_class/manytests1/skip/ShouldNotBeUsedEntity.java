package jpa.eclipselink.test_hierarchy.no_base_class.manytests1.skip;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
class ShouldNotBeUsedEntity {
    static {
        if (1 > 0) {
            throw new IllegalStateException();
        }
    }

    @Id
    private Long id;

    public ShouldNotBeUsedEntity() {
        throw new IllegalStateException();
    }
}
