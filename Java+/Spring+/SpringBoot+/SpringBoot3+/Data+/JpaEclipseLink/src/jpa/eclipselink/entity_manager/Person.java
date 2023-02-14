package jpa.eclipselink.entity_manager;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;

@Entity(name = "PersonWhere")
@Table(name = "persons_where")
class Person {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private Integer age;
    private Boolean alive;

    public Person() {
    }

    public Person(String name, Integer age, Boolean alive) {
        this.name = name;
        this.age = age;
        this.alive = alive;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public Boolean getAlive() {
        return alive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) && Objects.equals(name, person.name) && Objects.equals(age, person.age)
                && Objects.equals(alive, person.alive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, alive);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", alive=" + alive +
                '}';
    }
}
