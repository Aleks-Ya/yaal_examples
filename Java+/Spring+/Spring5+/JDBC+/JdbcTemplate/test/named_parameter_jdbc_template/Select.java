package named_parameter_jdbc_template;

import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import util.TestBase;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Выборка данных из БД с помощью NamedParameterJdbcTemplate.
 */
public class Select extends TestBase {

    @Test
    public void queryForObject() {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("title_param", "Ben");
        parameters.addValue("id_param", 4);

        assertEquals("Ben", namedTemplate.queryForObject(
                "SELECT title FROM names WHERE id=:id_param AND title=:title_param",
                parameters, String.class));
    }

    @Test
    public void whereInParameter() {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("ids", Arrays.asList(1, 4));
        List<String> list = namedTemplate.queryForList("SELECT title FROM names WHERE id IN(:ids)", parameters, String.class);
        assertThat(list, containsInAnyOrder("John", "Ben"));
    }
}