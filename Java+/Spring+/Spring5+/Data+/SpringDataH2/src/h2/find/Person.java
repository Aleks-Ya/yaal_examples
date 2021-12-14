package h2.find;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity(name = "PersonFind")
@Table(name = "persons_find")
class Person {
    @Id
    private Long id;
    private String name;
    private Integer age;
    private Boolean married;

    public Person() {
    }

    public Person(Long id, String name, Integer age, Boolean married) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.married = married;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public Boolean getMarried() {
        return married;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) && Objects.equals(name, person.name) && Objects.equals(age, person.age) && Objects.equals(married, person.married);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, married);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", married=" + married +
                '}';
    }
}
