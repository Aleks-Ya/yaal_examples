package h2.connection.init;

import org.junit.jupiter.api.Test;
import util.ResourceUtil;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SingleInitScript {

    @Test
    void asResource() throws SQLException {
        var initSqlScript = ResourceUtil.resourceToPath(SingleInitScript.class, "single_init_script.sql");
        var jdbcUrl = String.format("jdbc:h2:mem:;INIT=RUNSCRIPT FROM '%s'", initSqlScript);
        try (var conn = DriverManager.getConnection(jdbcUrl);
             var st = conn.createStatement();
             var rs = st.executeQuery("SELECT * FROM people")) {
            rs.next();
            assertEquals("John", rs.getString(2));
        }
    }

    @Test
    void inClasspath() throws SQLException {
        var jdbcUrl = "jdbc:h2:mem:;INIT=RUNSCRIPT FROM 'classpath:h2/connection/init/single_init_script.sql'";
        try (var conn = DriverManager.getConnection(jdbcUrl);
             var st = conn.createStatement();
             var rs = st.executeQuery("SELECT * FROM people")) {
            rs.next();
            assertEquals("John", rs.getString(2));
        }
    }


}