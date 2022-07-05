package named_parameter_jdbc_template;

import conf.Config;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Выборка данных из БД с помощью NamedParameterJdbcTemplate.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
class Select {

    @Autowired
    private NamedParameterJdbcTemplate template;

    @Test
    void queryForObject() {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("title_param", "Ben");
        parameters.addValue("id_param", 4);

        assertThat(template.queryForObject(
                "SELECT title FROM names WHERE id=:id_param AND title=:title_param",
                parameters, String.class)).isEqualTo("Ben");
    }

    @Test
    void whereInParameter() {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("ids", Arrays.asList(1, 4));
        List<String> list = template.queryForList("SELECT title FROM names WHERE id IN(:ids)", parameters, String.class);
        assertThat(list).containsExactlyInAnyOrder("John", "Ben");
    }
}