package liquibase.repo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(properties = "spring.config.location=classpath:liquibase/repo/SpringJpaTestTest.yaml")
class SpringJpaTestTest {

    @Autowired
    private PersonRepository repo;

    @Test
    void select() {
        var actList = repo.findAll();
        assertThat(actList).containsExactly(
                new Person(1L, "John", 30),
                new Person(2L, "Mary", 25)
        );
    }
}