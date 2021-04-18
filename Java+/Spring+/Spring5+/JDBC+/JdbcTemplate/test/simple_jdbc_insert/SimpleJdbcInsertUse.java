package simple_jdbc_insert;

import bean.Name;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.KeyHolder;
import util.TestBase;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

/**
 * Вставка строк в БД с помощью SimpleJdbcInsert.
 */
public class SimpleJdbcInsertUse extends TestBase {

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