import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;

/**
 * Создание первичного ключа в БД Derby.
 */
public class AutoincrementPrimaryKeyTest {

    @Test
    public void main() throws SQLException, ClassNotFoundException {
        //connect
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        Connection conn = DriverManager.getConnection(
                "jdbc:derby:memory:db_name;create=true", "", "");

        //insert
        Statement update = conn.createStatement();
        update.executeUpdate("CREATE TABLE numbers (" +
                "ID INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY, " +
                "numb INT)");
        update.executeUpdate("INSERT INTO numbers(numb) VALUES (3)");

        //select
        Statement select = conn.createStatement();
        ResultSet resultSet = select.executeQuery("SELECT * FROM numbers");
        if (resultSet.next()) {
            assertEquals(1, resultSet.getInt(1));
            assertEquals(3, resultSet.getInt(2));
        }

        //disconnect
        conn.close();
    }
}