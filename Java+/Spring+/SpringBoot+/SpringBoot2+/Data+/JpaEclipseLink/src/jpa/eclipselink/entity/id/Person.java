package jpa.eclipselink.entity.id;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity(name = "PersonEmbeddableId")
@Table(name = "persons_embeddable_id")
class Person {
    @Id
    private PersonId id;
    private String name;

    public Person() {
    }

    public Person(PersonId id, String name) {
        this.id = id;
        this.name = name;
    }

    public PersonId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) && Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
