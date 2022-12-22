package jpa.hibernate.test_config.manual_config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {Config.class, Person.class, PersonRepository.class})
class ManualConfigTest {
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
