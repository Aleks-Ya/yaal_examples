package h2.connection.mode.server;

import org.junit.jupiter.api.Test;
import util.FileUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.function.BiConsumer;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Convenient way to instantiate two H2 connections in the server mode with a Consumer.
 */
class H2ServerModeConsumerTest {

    private static void execute(BiConsumer<Connection, Connection> code) {
        var file = FileUtil.createAbsentTempFileDeleteOnExit(H2ServerModeConsumerTest.class.getSimpleName()).getAbsolutePath();
        var url = "jdbc:h2:" + file;
        try (var conn1 = DriverManager.getConnection(url + ";AUTO_SERVER=TRUE");
             var conn2 = DriverManager.getConnection(url)) {
            code.accept(conn1, conn2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void test() {
        execute((conn1, conn2) -> {
            try {
                var st1 = conn1.createStatement();
                st1.executeUpdate("CREATE TABLE numbers (numb INTEGER)");
                st1.executeUpdate("INSERT INTO numbers VALUES (3)");

                var st2 = conn2.createStatement();
                var rs = st2.executeQuery("SELECT * FROM numbers");
                if (rs.next()) {
                    assertThat(rs.getInt(1)).isEqualTo(3);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}