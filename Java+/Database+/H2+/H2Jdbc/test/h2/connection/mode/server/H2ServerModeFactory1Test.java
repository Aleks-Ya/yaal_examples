package h2.connection.mode.server;

import org.junit.jupiter.api.Test;
import util.FileUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Convenient way to instantiate two H2 connections in the server mode with a Factory.
 */
class H2ServerModeFactory1Test {

    @Test
    void test() throws SQLException {
        var h2 = H2Factory.newInstance();
        try (var conn1 = h2.getConn1();
             var conn2 = h2.getConn2()) {
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

    private static class H2Factory {
        private final Connection conn1;
        private final Connection conn2;

        private H2Factory() {
            var file = FileUtil.createAbsentTempFileDeleteOnExit(H2ServerModeFactory1Test.class.getSimpleName()).getAbsolutePath();
            var url = "jdbc:h2:" + file;
            try {
                conn1 = DriverManager.getConnection(url + ";AUTO_SERVER=TRUE");
                conn2 = DriverManager.getConnection(url);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        public static H2Factory newInstance() {
            return new H2Factory();
        }

        Connection getConn1() {
            return conn1;
        }

        Connection getConn2() {
            return conn2;
        }
    }
}