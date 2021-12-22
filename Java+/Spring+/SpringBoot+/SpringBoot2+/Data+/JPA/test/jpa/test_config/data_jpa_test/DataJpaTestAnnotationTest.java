package jpa.test_config.data_jpa_test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@EnableAutoConfiguration
@ContextConfiguration(classes = PersonRepository.class)
class DataJpaTestAnnotationTest {
    @Autowired
    private PersonRepository repo;

    @Test
    void findByName() {
        var name = "John";
        repo.save(new Person(name));
        var actPerson = repo.findByName(name);
        assertThat(actPerson).isEqualTo(new Person(1, name));
    }
}
