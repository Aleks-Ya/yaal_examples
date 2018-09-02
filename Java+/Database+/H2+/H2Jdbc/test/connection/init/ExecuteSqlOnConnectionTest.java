package connection.init;

import org.junit.Test;
import util.ResourceUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;

public class ExecuteSqlOnConnectionTest {

    @Test
    public void test() throws SQLException {
        String initSqlScript = ResourceUtil.resourceToPath(ExecuteSqlOnConnectionTest.class, "ExecuteSqlOnConnectionTest.sql");
        String jdbcUrl = String.format("jdbc:h2:mem:;INIT=RUNSCRIPT FROM '%s'", initSqlScript);
        Connection conn = DriverManager.getConnection(jdbcUrl);

        Statement select = conn.createStatement();
        ResultSet resultSet = select.executeQuery("SELECT * FROM people");
        if (resultSet.next()) {
            assertEquals("John", resultSet.getString(2));
        }

        conn.close();
    }
}