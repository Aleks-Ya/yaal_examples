package row_mapper;

import bean.Name;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.RowMapper;
import util.TestBase;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Custom implementation of {@link RowMapper}.
 */
class BeanPropertyRowMapperTest extends TestBase {

    @Test
    void customRowMapper() {
        RowMapper<Name> rowMapper = new NameRowMapper();
        var name = template.queryForObject("SELECT * FROM names WHERE id=1", rowMapper);
        assertThat((int) name.getId()).isEqualTo(1);
        assertThat(name.getTitle()).isEqualTo("John");
    }

    private static class NameRowMapper implements RowMapper<Name> {

        @Override
        public Name mapRow(ResultSet rs, int rowNum) throws SQLException {
            var id = rs.getInt("id");
            var title = rs.getString("title");
            return new Name(id, title);
        }
    }

}