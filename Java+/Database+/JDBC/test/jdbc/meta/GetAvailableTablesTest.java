package jdbc.meta;

import org.junit.jupiter.api.Test;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Get all available tables via JDBC.
 */
class GetAvailableTablesTest {

    @Test
    void getAllTables() throws SQLException {
        try (var conn = DriverManager.getConnection("jdbc:h2:mem:");
             var statement = conn.createStatement()) {
            statement.execute("CREATE TABLE abc (id int)");

            var meta = conn.getMetaData();
            try (var tables = meta.getTables(null, null, null, null)) {
                while (tables.next()) {
                    var schema = tables.getString("TABLE_SCHEM");
                    var name = tables.getString("TABLE_NAME");
                    System.out.println(schema + "-" + name);
                }
            }
        }
    }
}