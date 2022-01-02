package h2.log;

import org.junit.jupiter.api.Test;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Set log level at runtime.
 */
class LogLevelRuntime {

    @Test
    void changeLogLevel() throws SQLException {
        try (var conn = DriverManager.getConnection("jdbc:h2:mem:");
             var st = conn.createStatement()) {
            System.out.println("\n=========== Set DEBUG log level ===========");
            st.execute("SET TRACE_LEVEL_SYSTEM_OUT 3");//DEBUG
            st.executeUpdate("CREATE TABLE numbers (numb INTEGER)");

            System.out.println("\n=========== Set INFO log level ===========");
            st.execute("SET TRACE_LEVEL_SYSTEM_OUT 2");//INFO
            st.executeUpdate("INSERT INTO numbers VALUES (3)");

            System.out.println("\n=========== Set ERROR log level ===========");
            st.execute("SET TRACE_LEVEL_SYSTEM_OUT 1");//ERROR
            var rs = st.executeQuery("SELECT * FROM numbers");
            if (rs.next()) {
                assertEquals(3, rs.getInt(1));
            }
        }
    }
}