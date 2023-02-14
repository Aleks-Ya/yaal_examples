package jpa.eclipselink.test_hierarchy.no_base_class.manytests1.many_b;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
class VehicleB {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    public VehicleB() {
    }

    public VehicleB(String name) {
        this.name = name;
    }

    public VehicleB(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VehicleB vehicle2 = (VehicleB) o;
        return Objects.equals(id, vehicle2.id) && Objects.equals(name, vehicle2.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
