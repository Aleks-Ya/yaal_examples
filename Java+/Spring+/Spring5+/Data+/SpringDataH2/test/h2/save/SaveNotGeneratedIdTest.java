package h2.save;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {Config.class, Person.class, PersonRepository.class})
class SaveNotGeneratedIdTest {
    @Autowired
    private PersonRepository repo;

    @Test
    void save() {
        var person = new Person(1L, "John");
        var personSaved = repo.save(person);
        assertThat(person).isEqualTo(personSaved);
        assertThat(repo.findById(personSaved.getId())).contains(personSaved);
    }

    @Test
    void update() {
        var person = new Person(1L, "John");
        var personSaved = repo.save(person);
        assertThat(repo.findById(personSaved.getId())).contains(personSaved);
        var newName = "Mark";
        personSaved.setName(newName);
        repo.save(personSaved);
        assertThat(repo.findById(personSaved.getId()).orElseThrow()).isEqualTo(personSaved);
    }
}
