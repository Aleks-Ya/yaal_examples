package jpa.entity.lifecycle;

import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import java.util.ArrayList;
import java.util.List;

public class EventListener {
    public static final List<String> events = new ArrayList<>();

    @PrePersist
    private void prePersist(Person2 person) {
        events.add(PrePersist.class.getSimpleName() + ": " + person);
    }

    @PreUpdate
    private void preUpdate(Person2 person) {
        events.add(PreUpdate.class.getSimpleName() + ": " + person);
    }

    @PreRemove
    private void preRemove(Person2 person) {
        events.add(PreRemove.class.getSimpleName() + ": " + person);
    }

    @PostPersist
    private void postPersist(Person2 person) {
        events.add(PostPersist.class.getSimpleName() + ": " + person);
    }

    @PostUpdate
    private void postUpdate(Person2 person) {
        events.add(PostUpdate.class.getSimpleName() + ": " + person);
    }

    @PostRemove
    private void postRemove(Person2 person) {
        events.add(PostRemove.class.getSimpleName() + ": " + person);
    }

    @PostLoad
    private void postLoad(Person2 person) {
        events.add(PostLoad.class.getSimpleName() + ": " + person);
    }
}
