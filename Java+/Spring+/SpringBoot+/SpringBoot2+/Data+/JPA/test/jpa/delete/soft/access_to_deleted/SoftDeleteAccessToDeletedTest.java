package jpa.delete.soft.access_to_deleted;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {Config.class, Person.class, PersonRepository.class, PersonService.class})
class SoftDeleteAccessToDeletedTest {
    @Autowired
    private PersonService service;

    @Test
    void delete() {
        var actPerson = service.create(new Person("John"));
        assertThat(service.findAll(false)).hasSize(1);
        assertThat(service.findAll(true)).hasSize(1);
        service.remove(actPerson.getId());
        assertThat(service.findAll(false)).isEmpty();
        assertThat(service.findAll(true)).hasSize(1);
    }
}
