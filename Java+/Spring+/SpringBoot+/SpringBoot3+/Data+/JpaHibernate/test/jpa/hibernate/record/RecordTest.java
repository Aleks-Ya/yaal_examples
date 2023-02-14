package jpa.hibernate.record;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@EnableAutoConfiguration
@ContextConfiguration(classes = {Person.class, PersonRepository.class})
class RecordTest {
    @Autowired
    private PersonRepository repo;

    /**
     * It's impossible to user Records as JPA entities.
     */
    @Test
    void findByName() {
        assertThrows(JpaSystemException.class, () -> repo.save(new Person("John")));
    }
}
