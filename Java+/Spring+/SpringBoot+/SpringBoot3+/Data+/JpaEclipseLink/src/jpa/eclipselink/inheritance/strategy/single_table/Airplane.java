package jpa.eclipselink.inheritance.strategy.single_table;

import javax.persistence.Entity;
import java.util.Objects;

@Entity
class Airplane extends Vehicle {
    private Integer maxHeight;

    public Airplane() {
    }

    public Airplane(Long id, String name, Integer maxHeight) {
        super(id, name);
        this.maxHeight = maxHeight;
    }

    public Integer getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(Integer maxHeight) {
        this.maxHeight = maxHeight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Airplane airplane = (Airplane) o;
        return Objects.equals(maxHeight, airplane.maxHeight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), maxHeight);
    }
}
