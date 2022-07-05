package simple_jdbc_insert;

import bean.Name;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import util.TestBase;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Вставка строк в БД с помощью SimpleJdbcInsert.
 */
class SimpleJdbcInsertUse extends TestBase {

    private SimpleJdbcInsert insert;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        insert = new SimpleJdbcInsert(dataSource)
                .withTableName("names")
                .usingGeneratedKeyColumns("id");
    }

    @Test
    void map() {
        Map<String, Object> args = new HashMap<>();
        args.put("id", null);
        args.put("title", "Vera");

        var n = insert.executeAndReturnKey(args);
        assertThat(n.intValue()).isNotNull();

        var holder = insert.executeAndReturnKeyHolder(args);
        assertThat(holder.getKey().intValue()).isNotNull();
    }

    @Test
    void mapSqlParameterSource() {
        var map = new MapSqlParameterSource()
                .addValue("id", null)
                .addValue("title", "Vera");

        var n = insert.executeAndReturnKey(map);
        assertThat(n.intValue()).isNotNull();

        var holder = insert.executeAndReturnKeyHolder(map);
        assertThat(holder.getKey().intValue()).isNotNull();
    }

    @Test
    void beanPropertySqlParameterSource() {
        var name = new Name();
        name.setTitle("Vera");
        SqlParameterSource map = new BeanPropertySqlParameterSource(name);

        var n = insert.executeAndReturnKey(map);
        assertThat(n.intValue()).isNotNull();

        var holder = insert.executeAndReturnKeyHolder(map);
        assertThat(holder.getKey().intValue()).isNotNull();
    }
}