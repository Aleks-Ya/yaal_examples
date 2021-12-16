package jpa.delete.soft.no_access_to_deleted;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@EnableAutoConfiguration
@ContextConfiguration(classes = {Person.class, PersonRepository.class, PersonService.class})
class SoftDeleteNoAccessToDeletedTest {
    @Autowired
    private PersonService service;

    @Test
    void delete() {
        var actPerson = service.create(new Person("John"));
        assertThat(service.findAll()).hasSize(1);
        service.remove(actPerson.getId());
        assertThat(service.findAll()).isEmpty();
    }
}
