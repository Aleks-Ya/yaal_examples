package jpa.eclipselink.entity.id.embeddable.no_converter;

import jdbc.JdbcUtil;
import jpa.eclipselink.BaseEclipseLinkTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * ID field {@link PersonId} is stored into an "BINARY" column due.
 */
@EnableAutoConfiguration
class EmbeddableIdTest extends BaseEclipseLinkTest {
    @Autowired
    private PersonRepository repo;
    @Autowired
    private DataSource dataSource;

    @Test
    void findByName() {
        var name = "John";
        var id = new PersonId(1);
        repo.save(new Person(id, name));
        var actPerson = repo.findById(id).orElseThrow();
        assertThat(actPerson).isEqualTo(new Person(id, name));

        var columnType = JdbcUtil.getColumnMetaData(dataSource, "PERSONS_EMBEDDABLE_ID").get("ID").typeName();
        assertThat(columnType).isEqualTo("BINARY VARYING");
    }
}
