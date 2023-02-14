package jpa.hibernate.delete.soft.access_to_deleted;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;

import static jpa.hibernate.delete.soft.access_to_deleted.Person.FILTER;

@Entity(name = "PersonDeleteSoft")
@Table(name = "persons_delete_soft")
@SQLDelete(sql = "UPDATE persons_delete_soft SET deleted = true WHERE id=?")
@FilterDef(name = FILTER, parameters = @ParamDef(name = Person.IS_DELETED_PARAM, type = Boolean.class))
@Filter(name = FILTER, condition = "deleted = :" + Person.IS_DELETED_PARAM)
class Person {
    public static final String FILTER = "deletedPersonFilter";
    public static final String IS_DELETED_PARAM = "isDeleted";

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
