package connection.init;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static java.lang.String.format;
import static org.junit.Assert.assertEquals;
import static util.ResourceUtil.resourceToPath;

public class MultiInitScript {

    @Test
    public void asResource() throws SQLException {
        String schemaInitScript = resourceToPath(MultiInitScript.class, "multi_init_script_schema.sql");
        String dataInitScript = resourceToPath(MultiInitScript.class, "multi_init_script_data.sql");
        String jdbcUrl = format("jdbc:h2:mem:;INIT=RUNSCRIPT FROM '%s'\\;RUNSCRIPT FROM '%s'", schemaInitScript, dataInitScript);

        try (Connection conn = DriverManager.getConnection(jdbcUrl);
             Statement select = conn.createStatement();
             ResultSet resultSet = select.executeQuery("SELECT * FROM people")) {
            resultSet.next();
            assertEquals("John", resultSet.getString(2));
        }
    }

    @Test
    public void inClasspath() throws SQLException {
        String schemaScript = "classpath:connection/init/multi_init_script_schema.sql";
        String dataScript = "classpath:connection/init/multi_init_script_data.sql";
        String jdbcUrl = format("jdbc:h2:mem:;INIT=RUNSCRIPT FROM '%s'\\;RUNSCRIPT FROM '%s'", schemaScript, dataScript);

        try (Connection conn = DriverManager.getConnection(jdbcUrl);
             Statement select = conn.createStatement();
             ResultSet resultSet = select.executeQuery("SELECT * FROM people")) {
            resultSet.next();
            assertEquals("John", resultSet.getString(2));
        }
    }

}