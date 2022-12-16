package jpa.entity.id;

import org.junit.jupiter.api.Test;

import java.util.List;

import static jpa.JpaHelper.withEntityManager;
import static org.assertj.core.api.Assertions.assertThat;

class IdFieldTest {
    @Test
    void transientField() {
        withEntityManager((em) -> {
            em.getTransaction().begin();
            var id = 1L;
            var person = new PersonEntity(id, "John");
            em.persist(person);
            assertThat(em.find(PersonEntity.class, id)).isEqualTo(new PersonEntity(id, "John"));
            em.getTransaction().commit();
        }, List.of(PersonEntity.class));
    }
}