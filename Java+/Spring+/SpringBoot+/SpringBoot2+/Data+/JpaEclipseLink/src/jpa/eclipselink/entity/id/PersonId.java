package jpa.eclipselink.entity.id;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
class PersonId implements Serializable {
    private Integer id;

    public PersonId() {
    }

    public PersonId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonId personId = (PersonId) o;
        return Objects.equals(id, personId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
