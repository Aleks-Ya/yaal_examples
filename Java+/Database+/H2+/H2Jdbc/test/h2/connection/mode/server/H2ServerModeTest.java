package h2.connection.mode.server;

import org.junit.jupiter.api.Test;
import util.FileUtil;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Run H2 in Sever Mode.
 * It allows open several connections to single database.
 */
class H2ServerModeTest {

    @Test
    void test() throws SQLException {
        var file = FileUtil.createAbsentTempFileDeleteOnExit(H2ServerModeTest.class.getSimpleName()).getAbsolutePath();
        var url = "jdbc:h2:" + file;
        try (var conn1 = DriverManager.getConnection(url + ";AUTO_SERVER=TRUE");
             var conn2 = DriverManager.getConnection(url)) {
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
}