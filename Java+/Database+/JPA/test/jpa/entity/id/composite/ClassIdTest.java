package jpa.entity.id.composite;

import org.junit.jupiter.api.Test;

import java.util.List;

import static jpa.JpaHelper.withEntityManager;
import static jpa.entity.id.composite.Person2Entity.Gender.MALE;
import static org.assertj.core.api.Assertions.assertThat;

class ClassIdTest {
    @Test
    void classId() {
        withEntityManager((em) -> {
            em.getTransaction().begin();
            var person = new Person2Entity("John", "Smith", MALE, 30);
            em.persist(person);
            var idObject = new Person2Entity.Person2Id(person.getFirstName(), person.getSecondName(), MALE);
            assertThat(em.find(Person2Entity.class, idObject)).isEqualTo(new Person2Entity("John", "Smith", MALE, 30));
            em.getTransaction().commit();
        }, List.of(Person2Entity.class));
    }
}