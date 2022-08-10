package liquibase.repo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = "spring.config.location=classpath:liquibase/repo/application.yaml")
class LiquibaseRepoTest {

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