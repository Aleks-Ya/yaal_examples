package jpa.entity.enumeration;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.Transient;
import java.util.Objects;

import static jpa.entity.enumeration.Occupation4.DEVELOPER;
import static jpa.entity.enumeration.Occupation4.MANAGER;

@Entity
class Person4Entity {
    @Id
    private Long id;

    private String name;

    @Transient
    private Occupation4 occupation;

    @Basic
    private Integer occupationValue;

    public Person4Entity() {
    }

    public Person4Entity(Long id, String name, Occupation4 occupation) {
        this.id = id;
        this.name = name;
        this.occupation = occupation;
    }

    @PostLoad
    void fillTransient() {
        switch (occupationValue) {
            case 1 -> this.occupation = MANAGER;
            case 2 -> this.occupation = DEVELOPER;
            default -> throw new IllegalStateException();
        }
    }

    @PrePersist
    void fillPersistent() {
        if (occupationValue != null) {
            this.occupationValue = switch (occupation) {
                case MANAGER -> 1;
                case DEVELOPER -> 2;
            };
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Occupation4 getOccupation() {
        return occupation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person4Entity that = (Person4Entity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && occupation == that.occupation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, occupation);
    }
}
