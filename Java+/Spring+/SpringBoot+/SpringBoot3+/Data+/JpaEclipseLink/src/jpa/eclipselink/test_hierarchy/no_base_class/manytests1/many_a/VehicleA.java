package jpa.eclipselink.test_hierarchy.no_base_class.manytests1.many_a;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.util.Objects;

@Entity
class VehicleA {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    public VehicleA() {
    }

    public VehicleA(String name) {
        this.name = name;
    }

    public VehicleA(Long id, String name) {
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
        VehicleA vehicle1 = (VehicleA) o;
        return Objects.equals(id, vehicle1.id) && Objects.equals(name, vehicle1.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
