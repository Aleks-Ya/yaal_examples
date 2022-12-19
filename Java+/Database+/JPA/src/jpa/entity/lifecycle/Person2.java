package jpa.entity.lifecycle;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import java.util.Objects;

@Entity
@EntityListeners(EventListener.class)
class Person2 {
    @Id
    private Long id;

    private String name;

    public Person2() {
    }

    public Person2(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person2 that = (Person2) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String
    toString() {
        return "Person2{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
