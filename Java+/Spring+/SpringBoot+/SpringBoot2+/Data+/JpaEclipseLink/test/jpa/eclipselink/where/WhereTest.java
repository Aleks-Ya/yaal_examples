package jpa.eclipselink.where;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Use {@link org.hibernate.annotations.Where} annotation to restrict select output.
 */
@DataJpaTest
@EnableAutoConfiguration
@ContextConfiguration(classes = {Person.class, AdultPersonRepository.class})
class WhereTest {
    @Autowired
    private AdultPersonRepository repo;

    @Test
    void findByName() {
        var name1 = "John";
        var age1 = 21;
        var savedPerson1 = repo.save(new Person(name1, age1, true));

        var name2 = "Mary";
        var age2 = 17;
        repo.save(new Person(name2, age2, true));

        var name3 = "Patrik";
        var age3 = 95;
        repo.save(new Person(name3, age3, false));

        assertThat(repo.findByName(name1)).isEqualTo(savedPerson1);
        assertThat(repo.findByName(name2)).isNull();
        assertThat(repo.findByName(name3)).isNull();
    }
}
