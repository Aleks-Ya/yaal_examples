package data.postgres.datatype;

import data.postgres.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;


@ContextConfiguration(classes = PersonDataTypeRepository.class)
class TimestampTest extends BaseTest {
    @Autowired
    private PersonDataTypeRepository repo;

    @Test
    void instant() {
        var name = "John";
        var applicationDate = Instant.parse("2007-12-03T10:15:30.00Z");
        repo.save(new Person(name, applicationDate));
        var actPerson = repo.findByName(name);
        assertThat(actPerson).isEqualTo(new Person(1, name, applicationDate));
    }

}
