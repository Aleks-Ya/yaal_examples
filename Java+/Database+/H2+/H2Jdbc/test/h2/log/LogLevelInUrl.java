package h2.log;

import org.junit.jupiter.api.Test;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Set log level in H2 URL.
 */
class LogLevelInUrl {

    @Test
    void debugToStdOut() throws SQLException {
        runH2("TRACE_LEVEL_SYSTEM_OUT=3");
    }

    @Test
    void infoToStdOut() throws SQLException {
        runH2("TRACE_LEVEL_SYSTEM_OUT=2");
    }

    @Test
    void errorToStdOut() throws SQLException {
        runH2("TRACE_LEVEL_SYSTEM_OUT=1");//default
    }

    @Test
    void offToStdOut() throws SQLException {
        runH2("TRACE_LEVEL_SYSTEM_OUT=0");
    }

    private void runH2(String logLevel) throws SQLException {
        try (var conn = DriverManager.getConnection("jdbc:h2:mem:;" + logLevel);
             var st = conn.createStatement()) {
            st.executeUpdate("CREATE TABLE numbers (numb INTEGER)");
            st.executeUpdate("INSERT INTO numbers VALUES (3)");
            var rs = st.executeQuery("SELECT * FROM numbers");
            if (rs.next()) {
                assertEquals(3, rs.getInt(1));
            }
        }
    }
}