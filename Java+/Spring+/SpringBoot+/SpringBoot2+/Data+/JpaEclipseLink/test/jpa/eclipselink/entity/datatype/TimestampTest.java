package jpa.eclipselink.entity.datatype;

import jpa.eclipselink.BaseEclipseLinkTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;


@EnableAutoConfiguration
class TimestampTest extends BaseEclipseLinkTest {
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
