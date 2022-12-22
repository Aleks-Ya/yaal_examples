package jpa.eclipselink.delete.soft.no_access_to_deleted;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity(name = "PersonDeleteSoftNoAccessToDeleted")
@Table(name = "persons_delete_soft_no_access_to_deleted")
@SQLDelete(sql = "UPDATE persons_delete_soft_no_access_to_deleted SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
class Person {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private Boolean deleted = false;

    public Person() {
    }

    public Person(String name) {
        this.name = name;
    }

    public Person(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) && Objects.equals(name, person.name) && Objects.equals(deleted, person.deleted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, deleted);
    }
}
