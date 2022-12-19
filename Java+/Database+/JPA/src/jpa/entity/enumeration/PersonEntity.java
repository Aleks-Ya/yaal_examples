package jpa.entity.enumeration;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
class PersonEntity {
    @Id
    private Long id;

    private String name;

    private Occupation occupation;

    public PersonEntity() {
    }

    public PersonEntity(Long id, String name, Occupation occupation) {
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

    public Occupation getOccupation() {
        return occupation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonEntity that = (PersonEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && occupation == that.occupation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, occupation);
    }
}
