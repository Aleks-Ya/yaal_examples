package named_parameter_jdbc_template;

import conf.Config;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Выборка данных из БД с помощью NamedParameterJdbcTemplate.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Config.class)
public class Update {

    @Autowired
    private NamedParameterJdbcTemplate template;

    @Test
    void queryForObject() {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("title_param", "Ben");
        parameters.addValue("id_param", 4);

        assertEquals(1, template.update(
                "UPDATE names SET title=:title_param WHERE id=:id_param",
                parameters));
    }
}
