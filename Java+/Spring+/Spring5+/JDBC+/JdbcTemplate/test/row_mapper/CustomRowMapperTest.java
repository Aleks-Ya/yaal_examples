package row_mapper;

import bean.Name;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import util.TestBase;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Use {@link BeanPropertyRowMapper} to map rows to POJOs.
 */
public class CustomRowMapperTest extends TestBase {
    private static final RowMapper<Name> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Name.class);

    @Test
    public void singleObject() {
        Name name = template.queryForObject("SELECT * FROM names WHERE id=1", ROW_MAPPER);
        assertEquals(1, (int) name.getId());
        assertEquals("John", name.getTitle());
    }

    @Test
    public void objectList() {
        List<Name> names = template.query("SELECT * FROM names", ROW_MAPPER);

        Name name1 = names.get(0);
        assertEquals(1, (int) name1.getId());
        assertEquals("John", name1.getTitle());

        Name name2 = names.get(1);
        assertEquals(2, (int) name2.getId());
        assertEquals("Mary", name2.getTitle());
    }
}