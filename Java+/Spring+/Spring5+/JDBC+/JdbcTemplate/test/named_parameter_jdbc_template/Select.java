package named_parameter_jdbc_template;

import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import util.TestBase;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Выборка данных из БД с помощью NamedParameterJdbcTemplate.
 */
class Select extends TestBase {

    @Test
    void queryForObject() {
        var parameters = new MapSqlParameterSource();
        parameters.addValue("title_param", "Ben");
        parameters.addValue("id_param", 4);

        assertThat(namedTemplate.queryForObject(
                "SELECT title FROM names WHERE id=:id_param AND title=:title_param",
                parameters, String.class)).isEqualTo("Ben");
    }

    @Test
    void whereInParameter() {
        var parameters = new MapSqlParameterSource();
        parameters.addValue("ids", Arrays.asList(1, 4));
        var list = namedTemplate.queryForList("SELECT title FROM names WHERE id IN(:ids)", parameters, String.class);
        assertThat(list).containsExactlyInAnyOrder("John", "Ben");
    }
}