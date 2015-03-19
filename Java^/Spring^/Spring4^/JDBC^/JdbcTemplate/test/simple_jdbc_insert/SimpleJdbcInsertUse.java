package simple_jdbc_insert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

/**
 * Вставка строк в БД с помощью SimpleJdbcInsert.
 */
@ContextConfiguration("classpath:context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class SimpleJdbcInsertUse {

    @Autowired
    private JdbcTemplate template;

    private SimpleJdbcInsert insert;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        insert = new SimpleJdbcInsert(dataSource)
                .withTableName("names")
                .usingGeneratedKeyColumns("id");
    }

    @Test
    public void map() {
        Map<String, Object> args = new HashMap<>();
        args.put("id", null);
        args.put("title", "Vera");

        Number n = insert.executeAndReturnKey(args);
        assertNotNull(n.intValue());
    }

    @Test
    public void mapSqlParameterSource() {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", null)
                .addValue("title", "Vera");

        Number n = insert.executeAndReturnKey(map);
        assertNotNull(n.intValue());
    }
}