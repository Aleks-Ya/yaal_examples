package row_mapper;

import bean.Name;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.RowMapper;
import util.TestBase;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Custom implementation of {@link RowMapper}.
 */
public class BeanPropertyRowMapperTest extends TestBase {

    private static class NameRowMapper implements RowMapper<Name> {

        @Override
        public Name mapRow(ResultSet rs, int rowNum) throws SQLException {
            int id = rs.getInt("id");
            String title = rs.getString("title");
            return new Name(id, title);
        }
    }

    @Test
    public void customRowMapper() {
        RowMapper<Name> rowMapper = new NameRowMapper();
        Name name = template.queryForObject("SELECT * FROM names WHERE id=1", rowMapper);
        assertEquals(1, (int) name.getId());
        assertEquals("John", name.getTitle());
    }

}