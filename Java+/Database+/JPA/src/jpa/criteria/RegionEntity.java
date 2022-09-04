package jpa.criteria;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class RegionEntity {
    @Id
    private Long id;
    private String name;

    public RegionEntity() {
    }

    public RegionEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegionEntity that = (RegionEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return String.format("Регион[id=%d, name=%s]", id, name);
    }
}
