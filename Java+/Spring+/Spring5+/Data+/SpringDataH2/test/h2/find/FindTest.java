package h2.find;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {Config.class, Person.class, PersonRepository.class})
class FindTest {
    @Autowired
    private PersonRepository repo;

    @Test
    void findByName() {
        var name = "John";
        var person = new Person(1L, name, 30, false);
        repo.save(person);
        var actPerson = repo.findByName(name);
        assertThat(actPerson).isEqualTo(person);
    }

    @Test
    void findAllByName() {
        var name = "Mike";
        var person1 = new Person(2L, name, 30, false);
        var person2 = new Person(3L, name, 25, false);
        repo.save(person1);
        repo.save(person2);
        var persons = repo.findAllByName(name);
        assertThat(persons).containsExactly(person1, person2);
    }

    @Test
    void findAllByNameOrderByAge() {
        var name = "Peter";
        var person1 = new Person(4L, name, 30, false);
        var person2 = new Person(5L, name, 25, false);
        var person3 = new Person(6L, name, 20, false);
        repo.save(person1);
        repo.save(person2);
        repo.save(person3);

        var persons = repo.findAllByNameOrderByAge(name);
        assertThat(persons).containsExactly(person3, person2, person1);

        var personAsc = repo.findAllByNameOrderByAgeAsc(name);
        assertThat(personAsc).containsExactly(person3, person2, person1);

        var personsDesc = repo.findAllByNameOrderByAgeDesc(name);
        assertThat(personsDesc).containsExactly(person1, person2, person3);
    }

    @Test
    void findByNameAndAge() {
        var name1 = "George";
        var name2 = "Ludovic";
        var person1 = new Person(7L, name1, 30, false);
        var person2 = new Person(8L, name2, 25, false);
        var person3 = new Person(9L, name1, 20, false);
        repo.save(person1);
        repo.save(person2);
        repo.save(person3);
        var personOpt = repo.findByNameAndAge(name1, 20);
        assertThat(personOpt).hasValue(person3);
    }

    @Test
    void findAllByNameIn() {
        var name1 = "Joe";
        var name2 = "Mark";
        var name3 = "Patrik";
        var name4 = "Andrew";
        var person1 = new Person(10L, name1, 30, false);
        var person2 = new Person(11L, name2, 25, false);
        var person3 = new Person(12L, name3, 20, false);
        var person4 = new Person(13L, name4, 15, false);
        repo.save(person1);
        repo.save(person2);
        repo.save(person3);
        repo.save(person4);

        var persons1 = repo.findAllByNameIn(List.of(name1, name3));
        assertThat(persons1).containsExactly(person1, person3);

        var persons2 = repo.findByNameInAndAgeIn(List.of(name1, name3), List.of(30, 15));
        assertThat(persons2).containsExactly(person1);
    }

    @Test
    void findByMarriedTrue() {
        var name1 = "George";
        var name2 = "Ludovic";
        var person1 = new Person(14L, name1, 30, false);
        var person2 = new Person(15L, name2, 25, true);
        repo.save(person1);
        repo.save(person2);

        var married = repo.findByMarriedTrue();
        assertThat(married).containsExactly(person2);

        var notMarried = repo.findByMarriedFalse();
        assertThat(notMarried).containsExactly(person1);
    }
}
