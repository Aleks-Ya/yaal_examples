package jpa.delete.native_query;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@EnableAutoConfiguration
@ContextConfiguration(classes = Person.class)
class DeleteByNativeQueryTest {
    private final Person person1 = new Person("John");
    private final Person person2 = new Person("Jim");
    private final Person person3 = new Person("Mary");

    @Autowired
    private EntityManager em;

    @Test
    void deleteAll() {
        em.persist(person1);
        em.persist(person2);
        assertThat(findAllPersons()).containsExactly(person1, person2);

        var updatedRows = em.createNativeQuery("DELETE FROM Person p").executeUpdate();
        assertThat(updatedRows).isEqualTo(2);

        assertThat(findAllPersons()).isEmpty();
    }

    /**
     * Assert deleted rows with EntityManager#find() (requires refresh).
     */
    @Test
    void deleteAll_find() {
        em.persist(person1);
        var actPerson = em.find(Person.class, person1.getId());
        assertThat(actPerson).isEqualTo(person1);

        var updatedRows = em.createNativeQuery("DELETE FROM Person p").executeUpdate();
        assertThat(updatedRows).isEqualTo(1);

        try {
            em.refresh(actPerson);
        } catch (EntityNotFoundException ignored) {
        }
        assertThat(em.find(Person.class, person1.getId())).isNull();
    }

    @Test
    void deleteWhereLike() {
        em.persist(person1);
        em.persist(person2);
        em.persist(person3);
        assertThat(findAllPersons()).containsExactly(person1, person2, person3);

        var updatedRows = em.createNativeQuery("DELETE FROM Person p WHERE p.name LIKE 'J%'").executeUpdate();
        assertThat(updatedRows).isEqualTo(2);
        assertThat(findAllPersons()).containsExactly(person3);
    }

    @Test
    void deleteWhereEquals() {
        em.persist(person1);
        em.persist(person2);
        em.persist(person3);
        assertThat(findAllPersons()).containsExactly(person1, person2, person3);

        var updatedRows = em.createNativeQuery("DELETE FROM Person p WHERE p.name = 'Jim'").executeUpdate();
        assertThat(updatedRows).isEqualTo(1);
        assertThat(findAllPersons()).containsExactly(person1, person3);
    }

    @Test
    void deleteWhereIsNotNull() {
        var person4 = new Person(null);
        em.persist(person1);
        em.persist(person2);
        em.persist(person3);
        em.persist(person4);
        assertThat(findAllPersons()).containsExactly(person1, person2, person3, person4);

        var updatedRows = em.createNativeQuery("DELETE FROM Person p WHERE p.name IS NOT NULL").executeUpdate();
        assertThat(updatedRows).isEqualTo(3);
        assertThat(findAllPersons()).containsExactly(person4);
    }

    private List<Person> findAllPersons() {
        return em.createQuery("FROM Person", Person.class).getResultList();
    }

}
