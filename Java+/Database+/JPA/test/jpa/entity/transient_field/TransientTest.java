package jpa.entity.transient_field;

import org.junit.jupiter.api.Test;

import javax.persistence.Transient;
import java.util.List;

import static jpa.JpaHelper.withEntityManager;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Skip/ignore field with {@link Transient} annotation.
 */
class TransientTest {
    @Test
    void transientField() {
        withEntityManager((em) -> {
            em.getTransaction().begin();
            var originalPerson = new PersonEntity(1L, "John", 30);
            em.persist(originalPerson);
            em.getTransaction().commit();
            assertThat(originalPerson).isEqualTo(new PersonEntity(1L, "John", 30));
            em.clear();
            var actPerson = em.find(PersonEntity.class, originalPerson.getId());
            assertThat(actPerson).isEqualTo(new PersonEntity(1L, "John", null));
        }, List.of(PersonEntity.class));
    }
}