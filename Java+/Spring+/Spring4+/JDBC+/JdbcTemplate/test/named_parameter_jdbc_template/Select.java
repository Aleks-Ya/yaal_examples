package named_parameter_jdbc_template;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

/**
 * Выборка данных из БД с помощью NamedParameterJdbcTemplate.
 */
@ContextConfiguration("classpath:context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class Select {

    @Autowired
    private NamedParameterJdbcTemplate template;

    @Test
    public void queryForObject() {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("title_param", "Ben");
        parameters.addValue("id_param", 4);

        assertEquals("" +
                "Ben", template.queryForObject(
                "SELECT title FROM names WHERE id=:id_param AND title=:title_param",
                parameters, String.class));
    }
}