package h2.delete.plain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {Config.class, Person.class, PersonRepository.class})
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
