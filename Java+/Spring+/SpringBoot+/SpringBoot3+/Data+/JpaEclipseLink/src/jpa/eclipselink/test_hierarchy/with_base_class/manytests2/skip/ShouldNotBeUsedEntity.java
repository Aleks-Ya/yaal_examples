package jpa.eclipselink.test_hierarchy.with_base_class.manytests2.skip;

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
