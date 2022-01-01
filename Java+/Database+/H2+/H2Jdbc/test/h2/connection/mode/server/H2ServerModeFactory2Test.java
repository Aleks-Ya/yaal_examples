package h2.connection.mode.server;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static util.FileUtil.createAbsentTempFileDeleteOnExit;

/**
 * Convenient way to instantiate two H2 connections in the server mode with a Factory.
 */
class H2ServerModeFactory2Test {

    @Test
    void test() throws SQLException {
        var h2 = H2Server.create();
        try (var conn1 = h2.openConn();
             var conn2 = h2.openConn()) {
            var st1 = conn1.createStatement();
            st1.executeUpdate("CREATE TABLE numbers (numb INTEGER)");
            st1.executeUpdate("INSERT INTO numbers VALUES (3)");

            var st2 = conn2.createStatement();
            var rs = st2.executeQuery("SELECT * FROM numbers");
            if (rs.next()) {
                assertThat(rs.getInt(1)).isEqualTo(3);
            }
        }
    }

    private static class H2Server {
        private final String url;
        private boolean serverStarted = false;

        private H2Server() {
            url = "jdbc:h2:" + createAbsentTempFileDeleteOnExit(H2Server.class.getSimpleName())
                    .getAbsolutePath();
        }

        public static H2Server create() {
            return new H2Server();
        }

        synchronized Connection openConn() throws SQLException {
            if (serverStarted) {
                return DriverManager.getConnection(url);
            } else {
                serverStarted = true;
                return DriverManager.getConnection(url + ";AUTO_SERVER=TRUE");
            }
        }
    }
}