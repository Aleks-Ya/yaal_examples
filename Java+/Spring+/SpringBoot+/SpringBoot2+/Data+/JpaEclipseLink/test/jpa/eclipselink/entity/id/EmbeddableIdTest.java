package jpa.eclipselink.entity.id;

import jpa.eclipselink.BaseEclipseLinkTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@EnableAutoConfiguration
class EmbeddableIdTest extends BaseEclipseLinkTest {
    @Autowired
    private PersonRepository repo;

    @Test
    void findByName() {
        var name = "John";
        var id = new PersonId(1);
        repo.save(new Person(id, name));
        var actPerson = repo.findById(id).orElseThrow();
        assertThat(actPerson).isEqualTo(new Person(id, name));
    }
}
