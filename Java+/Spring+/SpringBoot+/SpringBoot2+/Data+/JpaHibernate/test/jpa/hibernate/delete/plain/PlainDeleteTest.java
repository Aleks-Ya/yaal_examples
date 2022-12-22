package jpa.hibernate.delete.plain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@EnableAutoConfiguration
@ContextConfiguration(classes = {Person.class, PersonRepository.class})
class PlainDeleteTest {
    @Autowired
    private PersonRepository repo;

    @Test
    void delete() {
        var actPerson = repo.save(new Person("John"));
        assertThat(repo.findById(actPerson.getId())).isPresent();
        repo.delete(actPerson);
        assertThat(repo.findById(actPerson.getId())).isNotPresent();
    }
}
