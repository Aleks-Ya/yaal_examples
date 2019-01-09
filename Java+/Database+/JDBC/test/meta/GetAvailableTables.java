package meta;

import org.junit.Test;

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
    public void test() throws SQLException {
        try (Connection conn = DriverManager.getConnection("jdbc:h2:mem:")) {

            Statement statement = conn.createStatement();
            statement.execute("CREATE TABLE abc (id int)");
            statement.close();

            DatabaseMetaData meta = conn.getMetaData();
            ResultSet tables = meta.getTables(null, null, null, null);
            while (tables.next()) {
                String schema = tables.getString(2);
                String name = tables.getString(3);
                System.out.println(schema + "-" + name);
            }
        }
    }
}