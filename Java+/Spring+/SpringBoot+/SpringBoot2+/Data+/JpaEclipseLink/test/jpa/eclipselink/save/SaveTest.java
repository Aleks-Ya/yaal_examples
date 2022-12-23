package jpa.eclipselink.save;

import jpa.eclipselink.BaseEclipseLinkTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@EnableAutoConfiguration
class SaveTest extends BaseEclipseLinkTest {
    @Autowired
    private PersonRepository repo;

    @Test
    void save() {
        var id = 1;
        var name = "John";
        var saved = repo.save(new Person(id, name));
        var actPerson = repo.findById(saved.getId());
        assertThat(actPerson).contains(new Person(id, name));
    }

    @Test
    void update() {
        var id = 2;

        var name1 = "John";
        var person1 = new Person(id, name1);
        repo.save(person1);
        assertThat(repo.findById(id)).contains(new Person(id, name1));

        var name2 = "Mark";
        var person2 = new Person(id, name2);
        repo.save(person2);
        assertThat(repo.findById(id)).contains(new Person(id, name2));
    }
}
