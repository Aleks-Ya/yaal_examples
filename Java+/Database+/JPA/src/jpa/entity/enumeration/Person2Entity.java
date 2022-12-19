package jpa.entity.enumeration;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.util.Objects;

@Entity
class Person2Entity {
    @Id
    private Long id;

    private String name;

    @Enumerated(EnumType.ORDINAL)
    private Occupation2 occupation;

    public Person2Entity() {
    }

    public Person2Entity(Long id, String name, Occupation2 occupation) {
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

    public Occupation2 getOccupation() {
        return occupation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person2Entity that = (Person2Entity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && occupation == that.occupation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, occupation);
    }
}
