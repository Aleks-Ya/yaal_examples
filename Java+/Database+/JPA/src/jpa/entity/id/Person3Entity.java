package jpa.entity.id;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.util.Objects;

@Entity
class Person3Entity {
    @Id
    private Person3Id id;

    private Integer age;

    public Person3Entity() {
    }

    public Person3Entity(Person3Id id, Integer age) {
        this.id = id;
        this.age = age;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person3Entity that = (Person3Entity) o;
        return Objects.equals(id, that.id) && Objects.equals(age, that.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, age);
    }
}
