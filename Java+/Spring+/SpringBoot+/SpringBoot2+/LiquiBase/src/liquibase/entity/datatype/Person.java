package liquibase.entity.datatype;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity(name = "Person")
@Table(name = "person")
class Person {
    @Id
    public Long id;
    public String name;
    public Integer age;
    public Instant created;
    public LocalDateTime updated;

    public Person() {
    }

    public Person(Long id, String name, Integer age, Instant created, LocalDateTime updated) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.created = created;
        this.updated = updated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) && Objects.equals(name, person.name) && Objects.equals(age, person.age)
                && Objects.equals(created, person.created) && Objects.equals(updated, person.updated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, created, updated);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", created=" + created +
                ", updated=" + updated +
                '}';
    }
}
