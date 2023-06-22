package liquibase.entity.datatype;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootConfiguration
@EnableAutoConfiguration
@SpringBootTest(properties = "spring.config.location=classpath:liquibase/entity/datatype/application.yaml")
class DataTypesTest {
    @Autowired
    private PersonRepository repo;

    @Test
    void select() {
        var actList = repo.findAll();
        assertThat(actList).containsExactly(
                new Person(1L, "John", 30,
                        Instant.parse("2023-06-22T12:30:50.00Z"),
                        LocalDateTime.parse("2023-07-23T13:31:51")),
                new Person(2L, "Mary", 25,
                        Instant.parse("2022-05-11T11:20:40.00Z"),
                        LocalDateTime.parse("2022-06-24T15:33:45"))
        );
    }
}