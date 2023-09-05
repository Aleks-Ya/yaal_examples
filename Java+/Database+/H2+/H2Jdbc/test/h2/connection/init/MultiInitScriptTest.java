package h2.connection.init;

import org.junit.jupiter.api.Test;
import util.ResourceUtil;

import java.sql.DriverManager;
import java.sql.SQLException;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

class MultiInitScriptTest {

    @Test
    void asResource() throws SQLException {
        var schemaInitScript = ResourceUtil.resourceToStrPath(MultiInitScriptTest.class, "multi_init_script_schema.sql");
        var dataInitScript = ResourceUtil.resourceToStrPath(MultiInitScriptTest.class, "multi_init_script_data.sql");
        var jdbcUrl = format("jdbc:h2:mem:;INIT=RUNSCRIPT FROM '%s'\\;RUNSCRIPT FROM '%s'", schemaInitScript, dataInitScript);
        try (var conn = DriverManager.getConnection(jdbcUrl);
             var st = conn.createStatement();
             var rs = st.executeQuery("SELECT * FROM people")) {
            rs.next();
            assertThat(rs.getString(2)).isEqualTo("John");
        }
    }

    @Test
    void inClasspath() throws SQLException {
        var schemaScript = "classpath:h2/connection/init/multi_init_script_schema.sql";
        var dataScript = "classpath:h2/connection/init/multi_init_script_data.sql";
        var jdbcUrl = format("jdbc:h2:mem:;INIT=RUNSCRIPT FROM '%s'\\;RUNSCRIPT FROM '%s'", schemaScript, dataScript);
        try (var conn = DriverManager.getConnection(jdbcUrl);
             var st = conn.createStatement();
             var rs = st.executeQuery("SELECT * FROM people")) {
            rs.next();
            assertThat(rs.getString(2)).isEqualTo("John");
        }
    }

}