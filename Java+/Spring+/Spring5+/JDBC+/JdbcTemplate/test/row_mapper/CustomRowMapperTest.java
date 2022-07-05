package row_mapper;

import bean.Name;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import util.TestBase;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Use {@link BeanPropertyRowMapper} to map rows to POJOs.
 */
class CustomRowMapperTest extends TestBase {
    private static final RowMapper<Name> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Name.class);

    @Test
    void singleObject() {
        var name = template.queryForObject("SELECT * FROM names WHERE id=1", ROW_MAPPER);
        assertThat((int) name.getId()).isEqualTo(1);
        assertThat(name.getTitle()).isEqualTo("John");
    }

    @Test
    void objectList() {
        var names = template.query("SELECT * FROM names", ROW_MAPPER);

        var name1 = names.get(0);
        assertThat((int) name1.getId()).isEqualTo(1);
        assertThat(name1.getTitle()).isEqualTo("John");

        var name2 = names.get(1);
        assertThat((int) name2.getId()).isEqualTo(2);
        assertThat(name2.getTitle()).isEqualTo("Mary");
    }
}