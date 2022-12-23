package jpa.eclipselink.delete.plain;

import jpa.eclipselink.BaseEclipseLinkTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@EnableAutoConfiguration
class PlainDeleteTest extends BaseEclipseLinkTest {
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
