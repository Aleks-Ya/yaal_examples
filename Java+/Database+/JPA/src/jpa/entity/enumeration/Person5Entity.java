package jpa.entity.enumeration;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
class Person5Entity {
    @Id
    private Long id;

    private String name;

    private Occupation5 occupation;

    public Person5Entity() {
    }

    public Person5Entity(Long id, String name, Occupation5 occupation) {
        this.id = id;
        this.name = name;
        this.occupation = occupation;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Occupation5 getOccupation() {
        return occupation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person5Entity that = (Person5Entity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && occupation == that.occupation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, occupation);
    }
}
