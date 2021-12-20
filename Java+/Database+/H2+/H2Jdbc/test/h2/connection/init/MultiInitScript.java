package h2.connection.init;

import org.junit.jupiter.api.Test;

import java.sql.DriverManager;
import java.sql.SQLException;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static util.ResourceUtil.resourceToPath;

class MultiInitScript {

    @Test
    void asResource() throws SQLException {
        var schemaInitScript = resourceToPath(MultiInitScript.class, "multi_init_script_schema.sql");
        var dataInitScript = resourceToPath(MultiInitScript.class, "multi_init_script_data.sql");
        var jdbcUrl = format("jdbc:h2:mem:;INIT=RUNSCRIPT FROM '%s'\\;RUNSCRIPT FROM '%s'", schemaInitScript, dataInitScript);
        try (var conn = DriverManager.getConnection(jdbcUrl);
             var st = conn.createStatement();
             var rs = st.executeQuery("SELECT * FROM people")) {
            rs.next();
            assertEquals("John", rs.getString(2));
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
            assertEquals("John", rs.getString(2));
        }
    }

}