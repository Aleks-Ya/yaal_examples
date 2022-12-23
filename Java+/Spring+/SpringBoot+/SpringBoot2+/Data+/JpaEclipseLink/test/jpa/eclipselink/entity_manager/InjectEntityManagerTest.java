package jpa.eclipselink.entity_manager;

import jpa.eclipselink.BaseEclipseLinkTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Inject and use {@link EntityManager}.
 */
@EnableAutoConfiguration
class InjectEntityManagerTest extends BaseEclipseLinkTest {
    @Autowired
    private EntityManager em;

    @Test
    void persist() {
        var expPerson = new PersonEM("John");
        em.persist(expPerson);
        var actPerson = em.find(PersonEM.class, expPerson.getId());
        assertThat(actPerson).isEqualTo(expPerson);
    }

    @Entity
    static class PersonEM {
        @Id
        @GeneratedValue
        private Integer id;
        private String name;

        public PersonEM() {
        }

        public PersonEM(String name) {
            this.name = name;
        }

        public Integer getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            var person = (PersonEM) o;
            return Objects.equals(id, person.id) && Objects.equals(name, person.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name);
        }

        @Override
        public String toString() {
            return "Person{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

}
