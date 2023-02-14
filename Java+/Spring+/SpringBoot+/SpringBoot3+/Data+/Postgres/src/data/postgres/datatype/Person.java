package data.postgres.datatype;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;
import java.util.Objects;

@Entity(name = "PersonDataType")
@Table(name = "persons_data_type")
class Person {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private Instant applicationDate;

    public Person() {
    }

    public Person(String name, Instant applicationDate) {
        this.name = name;
        this.applicationDate = applicationDate;
    }

    public Person(Integer id, String name, Instant applicationDate) {
        this.id = id;
        this.name = name;
        this.applicationDate = applicationDate;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Instant getApplicationDate() {
        return applicationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) && Objects.equals(name, person.name) && Objects.equals(applicationDate, person.applicationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, applicationDate);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", applicationDate=" + applicationDate +
                '}';
    }
}
