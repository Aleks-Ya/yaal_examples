package jpa.entity.lifecycle;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Person1 {
    @Transient
    public final List<Class<?>> events = new ArrayList<>();
    @Id
    private Long id;

    private String name;

    public Person1() {
    }

    public Person1(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @PrePersist
    public void prePersist() {
        events.add(PrePersist.class);
    }

    @PostPersist
    public void postPersist() {
        events.add(PostPersist.class);
    }

    @PreRemove
    public void preRemove() {
        events.add(PreRemove.class);
    }

    @PostRemove
    public void postRemove() {
        events.add(PostRemove.class);
    }

    @PreUpdate
    public void preUpdate() {
        events.add(PreUpdate.class);
    }

    @PostUpdate
    public void postUpdate() {
        events.add(PostUpdate.class);
    }

    @PostLoad
    public void postLoad() {
        events.add(PostLoad.class);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person1 that = (Person1) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
