package meta;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Get all available tables via JDBC.
 */
public class GetAvailableTables {

    @Test
    public void getAllTables() throws SQLException {
        try (Connection conn = DriverManager.getConnection("jdbc:h2:mem:");
             Statement statement = conn.createStatement()) {
            statement.execute("CREATE TABLE abc (id int)");

            DatabaseMetaData meta = conn.getMetaData();
            try (ResultSet tables = meta.getTables(null, null, null, null)) {
                while (tables.next()) {
                    String schema = tables.getString("TABLE_SCHEM");
                    String name = tables.getString("TABLE_NAME");
                    System.out.println(schema + "-" + name);
                }
            }
        }
    }
}