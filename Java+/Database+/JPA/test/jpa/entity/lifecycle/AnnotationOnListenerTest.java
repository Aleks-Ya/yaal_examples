package jpa.entity.lifecycle;

import org.junit.jupiter.api.Test;

import java.util.List;

import static jpa.JpaHelper.withEntityManager;
import static org.assertj.core.api.Assertions.assertThat;

class AnnotationOnListenerTest {
    @Test
    void persist() {
        EventListener.events.clear();
        withEntityManager((em) -> {
            em.getTransaction().begin();
            var expPerson = new Person2(1L, "John");
            em.persist(expPerson);
            var actPerson = em.find(Person2.class, expPerson.getId());
            assertThat(actPerson).isEqualTo(expPerson);
            assertThat(EventListener.events).containsExactly("PrePersist: Person2{id=1, name='John'}");
            em.getTransaction().commit();
            assertThat(EventListener.events).containsExactly(
                    "PrePersist: Person2{id=1, name='John'}", "PostPersist: Person2{id=1, name='John'}");
        }, List.of(Person2.class));
    }

    @Test
    void remove() {
        EventListener.events.clear();
        withEntityManager((em) -> {
            em.getTransaction().begin();
            var expPerson = new Person2(1L, "John");
            em.persist(expPerson);
            var actPerson = em.find(Person2.class, expPerson.getId());
            assertThat(actPerson).isEqualTo(expPerson);
            em.remove(expPerson);
            assertThat(EventListener.events).containsExactly(
                    "PrePersist: Person2{id=1, name='John'}", "PreRemove: Person2{id=1, name='John'}");
            em.getTransaction().commit();
            assertThat(EventListener.events).containsExactly(
                    "PrePersist: Person2{id=1, name='John'}", "PreRemove: Person2{id=1, name='John'}",
                    "PostPersist: Person2{id=1, name='John'}", "PostRemove: Person2{id=1, name='John'}");
        }, List.of(Person2.class));
    }

    @Test
    void update() {
        EventListener.events.clear();
        withEntityManager((em) -> {
            em.getTransaction().begin();
            var Person2 = new Person2(1L, "John");
            em.persist(Person2);
            em.getTransaction().commit();
            em.clear();
            em.getTransaction().begin();
            assertThat(EventListener.events).containsExactly(
                    "PrePersist: Person2{id=1, name='John'}", "PostPersist: Person2{id=1, name='John'}");
            var actPerson = em.find(Person2.class, Person2.getId());
            assertThat(actPerson).isEqualTo(Person2);
            var person2 = new Person2(Person2.getId(), "Mary");
            var merged = em.merge(person2);
            assertThat(EventListener.events).containsExactly("PrePersist: Person2{id=1, name='John'}",
                    "PostPersist: Person2{id=1, name='John'}", "PostLoad: Person2{id=1, name='John'}");
            em.flush();
            em.getTransaction().commit();
            assertThat(EventListener.events).containsExactly(
                    "PrePersist: Person2{id=1, name='John'}", "PostPersist: Person2{id=1, name='John'}",
                    "PostLoad: Person2{id=1, name='John'}", "PreUpdate: Person2{id=1, name='Mary'}",
                    "PostUpdate: Person2{id=1, name='Mary'}");
        }, List.of(Person2.class));
    }
}