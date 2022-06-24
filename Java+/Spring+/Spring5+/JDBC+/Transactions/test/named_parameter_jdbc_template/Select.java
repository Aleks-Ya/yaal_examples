package named_parameter_jdbc_template;

import conf.Config;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Выборка данных из БД с помощью NamedParameterJdbcTemplate.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Config.class)
public class Select {

    @Autowired
    private NamedParameterJdbcTemplate template;

    @Test
    void queryForObject() {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("title_param", "Ben");
        parameters.addValue("id_param", 4);

        assertEquals("" +
                "Ben", template.queryForObject(
                "SELECT title FROM names WHERE id=:id_param AND title=:title_param",
                parameters, String.class));
    }

    @Test
    void whereInParameter() {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("ids", Arrays.asList(1, 4));
        List<String> list = template.queryForList("SELECT title FROM names WHERE id IN(:ids)", parameters, String.class);
        assertThat(list, containsInAnyOrder("John", "Ben"));
    }
}