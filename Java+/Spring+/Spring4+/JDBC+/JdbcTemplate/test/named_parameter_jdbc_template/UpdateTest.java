package named_parameter_jdbc_template;

import conf.Config;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Выборка данных из БД с помощью NamedParameterJdbcTemplate.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
class UpdateTest {

    @Autowired
    private NamedParameterJdbcTemplate template;

    @Test
    void queryForObject() {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("title_param", "Ben");
        parameters.addValue("id_param", 4);

        assertThat(template.update(
                "UPDATE names SET title=:title_param WHERE id=:id_param",
                parameters)).isEqualTo(1);
    }
}
