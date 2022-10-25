package jpa.entity.stale;

import org.hibernate.StaleStateException;
import org.hibernate.UnresolvableObjectException;
import org.junit.jupiter.api.Test;

import javax.persistence.OptimisticLockException;
import java.util.List;

import static jpa.JpaHelper.withEntityManagerFactory;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StaleStateExceptionTest {
    @Test
    void remove() {
        withEntityManagerFactory((factory -> {
            var em1 = factory.createEntityManager();
            var em2 = factory.createEntityManager();

            em1.getTransaction().begin();
            var expPerson = new Person(1L, "John");
            em1.persist(expPerson);
            em1.getTransaction().commit();
            assertThat(em1.find(Person.class, expPerson.getId())).isEqualTo(expPerson);

            assertThat(em2.find(Person.class, expPerson.getId())).isEqualTo(expPerson);

            em1.getTransaction().begin();
            em1.remove(expPerson);
            em1.getTransaction().commit();
            assertThat(em1.find(Person.class, expPerson.getId())).isNull();

            assertThat(em2.find(Person.class, expPerson.getId())).isEqualTo(expPerson);
            assertThatThrownBy(() -> em2.refresh(expPerson))
                    .isInstanceOf(UnresolvableObjectException.class)
                    .hasMessage("No row with the given identifier exists: [jpa.entity.stale.Person#1]");
            em2.clear();
            assertThat(em2.find(Person.class, expPerson.getId())).isNull();
        }), List.of(Person.class));
    }

    @Test
    void stale() {
        withEntityManagerFactory((factory -> {
            var em1 = factory.createEntityManager();
            var em2 = factory.createEntityManager();

            em1.getTransaction().begin();
            var expPerson = new Person(1L, "John");
            em1.persist(expPerson);
            em1.getTransaction().commit();
            assertThat(em1.find(Person.class, expPerson.getId())).isEqualTo(expPerson);

            assertThat(em2.find(Person.class, expPerson.getId())).isEqualTo(expPerson);

            em1.getTransaction().begin();
            em1.remove(expPerson);
            em1.getTransaction().commit();
            assertThat(em1.find(Person.class, expPerson.getId())).isNull();

            em2.getTransaction().begin();
            assertThat(em2.find(Person.class, expPerson.getId())).isEqualTo(expPerson);
            em2.merge(new Person(1L, "Mary"));
            var expMessage = "Batch update returned unexpected row count from update [0]; actual row count: 0; expected: 1; statement executed: update Person set name=? where id=?";
            assertThatThrownBy(em2::flush)
                    .isInstanceOf(OptimisticLockException.class)
                    .hasMessage(expMessage)
                    .cause()
                    .isInstanceOf(StaleStateException.class)
                    .hasMessage(expMessage);
            em2.getTransaction().commit();
        }), List.of(Person.class));
    }
}