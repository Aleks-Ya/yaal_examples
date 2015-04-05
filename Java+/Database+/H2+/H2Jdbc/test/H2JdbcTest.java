import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;

public class H2JdbcTest {

    @Test
    public void main() throws SQLException, ClassNotFoundException {
        //connect
        Class.forName("org.h2.Driver");
        Connection conn = DriverManager.getConnection("jdbc:h2:mem:", "", "");

        //insert
        Statement update = conn.createStatement();
        update.executeUpdate("DROP TABLE IF EXISTS numbers");
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