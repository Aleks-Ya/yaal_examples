package named_parameter_jdbc_template;

import org.junit.Test;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import util.TestBase;

import static org.junit.Assert.assertEquals;

/**
 * Выборка данных из БД с помощью NamedParameterJdbcTemplate.
 */
public class Update extends TestBase {

    @Test
    public void queryForObject() {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("title_param", "Ben");
        parameters.addValue("id_param", 4);

        assertEquals(1, namedTemplate.update(
                "UPDATE names SET title=:title_param WHERE id=:id_param",
                parameters));
    }
}
