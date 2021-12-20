package h2.connection.mode.server;

import org.junit.jupiter.api.Test;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Run H2 in Sever Mode.
 * It allows open several connections to single database.
 */
class H2ServerModeTest {

    @Test
    void test() throws SQLException {

        try (var conn1 = DriverManager.getConnection("jdbc:h2:/tmp/servertest;AUTO_SERVER=TRUE");
             var conn2 = DriverManager.getConnection("jdbc:h2:/tmp/servertest")) {
            var st1 = conn1.createStatement();
            st1.executeUpdate("DROP TABLE IF EXISTS numbers");
            st1.executeUpdate("CREATE TABLE numbers (numb INTEGER)");
            st1.executeUpdate("INSERT INTO numbers VALUES (3)");

            var st2 = conn2.createStatement();
            var rs = st2.executeQuery("SELECT * FROM numbers");
            if (rs.next()) {
                assertEquals(3, rs.getInt(1));
            }
        }
    }
}