package jpa.eclipselink.test_config.data_jpa_test;

import jpa.eclipselink.JpaConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DirtiesContext
@EnableAutoConfiguration
@ContextConfiguration(classes = {PersonRepository.class, JpaConfig.class})
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
