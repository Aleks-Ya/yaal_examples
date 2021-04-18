package connection.mode.server;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;

/**
 * Run H2 in Sever Mode.
 * It allows open several connetions to single database.
 */
public class H2ServerModeTest {

    @Test
    public void test() throws SQLException {
        Connection conn1 = DriverManager.getConnection("jdbc:h2:/tmp/servertest;AUTO_SERVER=TRUE");
        Connection conn2 = DriverManager.getConnection("jdbc:h2:/tmp/servertest");

        Statement update = conn1.createStatement();
        update.executeUpdate("DROP TABLE IF EXISTS numbers");
        update.executeUpdate("CREATE TABLE numbers (numb INTEGER)");
        update.executeUpdate("INSERT INTO numbers VALUES (3)");

        Statement select = conn2.createStatement();
        ResultSet resultSet = select.executeQuery("SELECT * FROM numbers");
        if (resultSet.next()) {
            assertEquals(3, resultSet.getInt(1));
        }

        conn1.close();
        conn2.close();
    }
}