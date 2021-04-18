package simple_jdbc_insert;

import bean.Name;
import conf.Config;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Вставка строк в БД с помощью SimpleJdbcInsert.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Config.class)
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

        KeyHolder holder = insert.executeAndReturnKeyHolder(args);
        assertNotNull(holder.getKey().intValue());
    }

    @Test
    public void mapSqlParameterSource() {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", null)
                .addValue("title", "Vera");

        Number n = insert.executeAndReturnKey(map);
        assertNotNull(n.intValue());

        KeyHolder holder = insert.executeAndReturnKeyHolder(map);
        assertNotNull(holder.getKey().intValue());
    }

    @Test
    public void beanPropertySqlParameterSource() {
        Name name = new Name();
        name.setTitle("Vera");
        SqlParameterSource map = new BeanPropertySqlParameterSource(name);

        Number n = insert.executeAndReturnKey(map);
        assertNotNull(n.intValue());

        KeyHolder holder = insert.executeAndReturnKeyHolder(map);
        assertNotNull(holder.getKey().intValue());
    }
}