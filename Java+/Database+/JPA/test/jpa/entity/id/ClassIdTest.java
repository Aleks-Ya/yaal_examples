package jpa.entity.id;

import org.junit.jupiter.api.Test;

import java.util.List;

import static jpa.JpaHelper.withEntityManager;
import static org.assertj.core.api.Assertions.assertThat;

class ClassIdTest {
    @Test
    void classId() {
        withEntityManager((em) -> {
            em.getTransaction().begin();
            var person = new Person2Entity("John", "Smith", 30);
            em.persist(person);
            var idObject = new Person2Id(person.getFirstName(), person.getSecondName());
            assertThat(em.find(Person2Entity.class, idObject)).isEqualTo(new Person2Entity("John", "Smith", 30));
            em.getTransaction().commit();
        }, List.of(Person2Entity.class));
    }
}