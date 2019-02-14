package connection.init;

import org.junit.Test;
import util.ResourceUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;

public class SingleInitScript {

    @Test
    public void asResource() throws SQLException {
        String initSqlScript = ResourceUtil.resourceToPath(SingleInitScript.class, "single_init_script.sql");
        String jdbcUrl = String.format("jdbc:h2:mem:;INIT=RUNSCRIPT FROM '%s'", initSqlScript);

        try (Connection conn = DriverManager.getConnection(jdbcUrl);
             Statement select = conn.createStatement();
             ResultSet resultSet = select.executeQuery("SELECT * FROM people")) {
            resultSet.next();
            assertEquals("John", resultSet.getString(2));
        }
    }

    @Test
    public void inClasspath() throws SQLException {
        String jdbcUrl = "jdbc:h2:mem:;INIT=RUNSCRIPT FROM 'classpath:connection/init/single_init_script.sql'";

        try (Connection conn = DriverManager.getConnection(jdbcUrl);
             Statement select = conn.createStatement();
             ResultSet resultSet = select.executeQuery("SELECT * FROM people")) {
            resultSet.next();
            assertEquals("John", resultSet.getString(2));
        }
    }


}