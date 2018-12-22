package meta;

import org.junit.Test;

import java.sql.*;

/**
 * Get all available tables via JDBC.
 */
public class GetAvailableTables {

    @Test
    public void test() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:mem:");

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
        conn.close();
    }
}