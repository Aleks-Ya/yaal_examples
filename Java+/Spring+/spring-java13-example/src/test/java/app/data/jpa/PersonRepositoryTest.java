package app.data.jpa;

import app.BaseJpaTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertTrue;


class PersonRepositoryTest extends BaseJpaTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    void findAll() {
        Person person1 = personRepository.save(new Person());
        Person person2 = personRepository.save(new Person());
        Iterable<Person> allPersons = personRepository.findAll();
        assertThat(allPersons, contains(person1, person2));
    }

    @Test
    void save() {
        Person origin = new Person();
        String name = "John";
        origin.setName(name);
        Person exp = personRepository.save(origin);
        assertThat(exp.getId(), notNullValue());
        assertThat(exp.getName(), equalTo(name));
        Optional<Person> optAct = personRepository.findById(exp.getId());
        assertTrue(optAct.isPresent());
        Person act = optAct.get();
        assertThat(act, equalTo(exp));
    }
}