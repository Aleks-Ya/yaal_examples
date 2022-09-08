package h2.connection.mode.server;

import org.junit.jupiter.api.Test;
import util.FileUtil;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Run H2 in Sever Mode.
 * It allows open several connections to single database.
 */
class H2ServerModeTest {

    @Test
    void autoServer() throws SQLException {
        var file = FileUtil.createAbsentTempFileDeleteOnExit(H2ServerModeTest.class.getSimpleName()).getAbsolutePath();
        var url = "jdbc:h2:" + file + ";AUTO_SERVER=TRUE";
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
    }

    @Test
    void autoServerSeparately() throws SQLException {
        var file = FileUtil.createAbsentTempFileDeleteOnExit(H2ServerModeTest.class.getSimpleName()).getAbsolutePath();
        var url = "jdbc:h2:" + file;
        try (var conn1 = DriverManager.getConnection(url + ";AUTO_SERVER=TRUE")) {
            var st1 = conn1.createStatement();
            st1.executeUpdate("CREATE TABLE numbers (numb INTEGER)");
            st1.executeUpdate("INSERT INTO numbers VALUES (3)");
        }
        try (var conn2 = DriverManager.getConnection(url)) {
            var st2 = conn2.createStatement();
            var rs = st2.executeQuery("SELECT * FROM numbers");
            if (rs.next()) {
                assertThat(rs.getInt("numb")).isEqualTo(3);
            }
        }
    }

    @Test
    void autoServerDifferentThreads() throws ExecutionException, InterruptedException {
        var file = FileUtil.createAbsentTempFileDeleteOnExit(H2ServerModeTest.class.getSimpleName()).getAbsolutePath();
        var url = "jdbc:h2:" + file;

        var threadId1 = Executors.newSingleThreadExecutor().submit(() -> {
            try (var conn1 = DriverManager.getConnection(url + ";AUTO_SERVER=TRUE")) {
                var st1 = conn1.createStatement();
                st1.executeUpdate("CREATE TABLE numbers (numb INTEGER)");
                st1.executeUpdate("INSERT INTO numbers VALUES (3)");
                return Thread.currentThread().getId();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }).get();

        var threadId2 = Executors.newSingleThreadExecutor().submit(() -> {
            try (var conn2 = DriverManager.getConnection(url)) {
                var st2 = conn2.createStatement();
                var rs = st2.executeQuery("SELECT * FROM numbers");
                if (rs.next()) {
                    assertThat(rs.getInt("numb")).isEqualTo(3);
                }
                return Thread.currentThread().getId();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }).get();

        assertThat(threadId1).isNotEqualTo(threadId2);
    }

}