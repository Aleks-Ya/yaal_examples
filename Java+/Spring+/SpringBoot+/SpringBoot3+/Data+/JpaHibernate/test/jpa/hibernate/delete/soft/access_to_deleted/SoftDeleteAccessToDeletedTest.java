package jpa.hibernate.delete.soft.access_to_deleted;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@EnableAutoConfiguration
@ContextConfiguration(classes = {Person.class, PersonRepository.class, PersonService.class})
class SoftDeleteAccessToDeletedTest {
    @Autowired
    private PersonService service;

    @Test
    void delete() {
        var actPerson = service.create(new Person("John"));
        assertThat(service.findAll(false)).hasSize(1);
        assertThat(service.findAll(true)).isEmpty();
        service.remove(actPerson.getId());
        assertThat(service.findAll(false)).isEmpty();
        assertThat(service.findAll(true)).hasSize(1);
    }
}
