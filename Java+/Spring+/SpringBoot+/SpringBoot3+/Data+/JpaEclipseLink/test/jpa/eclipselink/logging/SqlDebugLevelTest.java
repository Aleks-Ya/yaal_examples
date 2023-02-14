package jpa.eclipselink.logging;

import jpa.eclipselink.BaseEclipseLinkTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@EnableAutoConfiguration
@TestPropertySource(properties = {//TODO replace Hibernate property with EclipseLink
        "spring.jpa.properties.hibernate.show_sql=false", //default value in tests is true, hide for test
        "logging.level.org.hibernate.SQL=DEBUG"})
class SqlDebugLevelTest extends BaseEclipseLinkTest {
    @Autowired
    private PersonRepository repo;

    @Test
    void findByName() {
        var name = "John";
        repo.save(new Person(name));
        var actPerson = repo.findByName(name);
        assertThat(actPerson).isEqualTo(new Person(1, name));
    }
}
