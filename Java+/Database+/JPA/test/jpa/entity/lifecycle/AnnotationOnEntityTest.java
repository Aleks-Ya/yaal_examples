package jpa.entity.lifecycle;

import org.junit.jupiter.api.Test;

import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import java.util.List;

import static jpa.JpaHelper.withEntityManager;
import static org.assertj.core.api.Assertions.assertThat;

class AnnotationOnEntityTest {
    @Test
    void persist() {
        withEntityManager((em) -> {
            em.getTransaction().begin();
            var expPerson = new Person1(1L, "John");
            em.persist(expPerson);
            var actPerson = em.find(Person1.class, expPerson.getId());
            assertThat(actPerson).isEqualTo(expPerson);
            assertThat(expPerson.events).containsExactly(PrePersist.class);
            em.getTransaction().commit();
            assertThat(expPerson.events).containsExactly(PrePersist.class, PostPersist.class);
        }, List.of(Person1.class));
    }

    @Test
    void remove() {
        withEntityManager((em) -> {
            em.getTransaction().begin();
            var expPerson = new Person1(1L, "John");
            em.persist(expPerson);
            var actPerson = em.find(Person1.class, expPerson.getId());
            assertThat(actPerson).isEqualTo(expPerson);
            em.remove(expPerson);
            assertThat(expPerson.events).containsExactly(PrePersist.class, PreRemove.class);
            em.getTransaction().commit();
            assertThat(expPerson.events).containsExactly(PrePersist.class, PreRemove.class, PostPersist.class, PostRemove.class);
        }, List.of(Person1.class));
    }

    @Test
    void update() {
        withEntityManager((em) -> {
            em.getTransaction().begin();
            var person1 = new Person1(1L, "John");
            em.persist(person1);
            em.getTransaction().commit();
            em.clear();
            em.getTransaction().begin();
            assertThat(person1.events).containsExactly(PrePersist.class, PostPersist.class);
            var actPerson = em.find(Person1.class, person1.getId());
            assertThat(actPerson).isEqualTo(person1);
            var person2 = new Person1(person1.getId(), "Mary");
            var merged = em.merge(person2);
            assertThat(person1.events).containsExactly(PrePersist.class, PostPersist.class);
            em.flush();
            em.getTransaction().commit();
            assertThat(person1.events).containsExactly(PrePersist.class, PostPersist.class);
            assertThat(merged.events).containsExactly(PostLoad.class, PreUpdate.class, PostUpdate.class);
        }, List.of(Person1.class));
    }
}