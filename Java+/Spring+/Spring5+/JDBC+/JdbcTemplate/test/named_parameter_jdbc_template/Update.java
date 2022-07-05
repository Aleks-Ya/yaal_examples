package named_parameter_jdbc_template;

import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import util.TestBase;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Выборка данных из БД с помощью NamedParameterJdbcTemplate.
 */
class Update extends TestBase {

    @Test
    void queryForObject() {
        var parameters = new MapSqlParameterSource();
        parameters.addValue("title_param", "Ben");
        parameters.addValue("id_param", 4);

        assertThat(namedTemplate.update(
                "UPDATE names SET title=:title_param WHERE id=:id_param",
                parameters)).isEqualTo(1);
    }
}
