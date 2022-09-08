package h2.connection.server;

import org.h2.tools.Server;
import org.junit.jupiter.api.Test;
import util.FileUtil;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Run H2 Server manually and connect to it.
 */
class StartServerManuallyTest {
    @Test
    void autoServer() throws SQLException {
        var server = Server.createTcpServer("-ifNotExists").start();
        var file = FileUtil.createAbsentTempFileDeleteOnExit(StartServerManuallyTest.class.getSimpleName()).getAbsolutePath();
        var url = String.format("jdbc:h2:tcp://localhost:%d/%s", server.getPort(), file);
        try (var conn1 = DriverManager.getConnection(url);
             var conn2 = DriverManager.getConnection(url)) {
            assertThat(conn1).isNotEqualTo(conn2);
            var st1 = conn1.createStatement();
            st1.executeUpdate("CREATE TABLE numbers (numb INTEGER)");
            st1.executeUpdate("INSERT INTO numbers VALUES (3)");

            var st2 = conn2.createStatement();
            var rs = st2.executeQuery("SELECT * FROM numbers");
            if (rs.next()) {
                assertThat(rs.getInt("numb")).isEqualTo(3);
            }
        }
        server.stop();
    }
}