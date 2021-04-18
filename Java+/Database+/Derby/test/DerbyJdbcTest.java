import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;

public class DerbyJdbcTest {

    @Test
    public void main() throws SQLException, ClassNotFoundException {
        //connect
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        Connection conn = DriverManager.getConnection(
                "jdbc:derby:memory:db_name;create=true", "", "");

        //insert
        Statement update = conn.createStatement();
        update.executeUpdate("CREATE TABLE numbers (numb INTEGER)");
        update.executeUpdate("INSERT INTO numbers VALUES (3)");

        //select
        Statement select = conn.createStatement();
        ResultSet resultSet = select.executeQuery("SELECT * FROM numbers");
        if (resultSet.next()) {
            assertEquals(3, resultSet.getInt(1));
        }

        //disconnect
        conn.close();
    }
}