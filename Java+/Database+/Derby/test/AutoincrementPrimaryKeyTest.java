import org.junit.jupiter.api.Test;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Создание первичного ключа в БД Derby.
 */
class AutoincrementPrimaryKeyTest {

    @Test
    void main() throws SQLException, ClassNotFoundException {
        //connect
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        var conn = DriverManager.getConnection(
                "jdbc:derby:memory:db_name;create=true", "", "");

        //insert
        var update = conn.createStatement();
        update.executeUpdate("CREATE TABLE numbers (" +
                "ID INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY, " +
                "numb INT)");
        update.executeUpdate("INSERT INTO numbers(numb) VALUES (3)");

        //select
        var select = conn.createStatement();
        var resultSet = select.executeQuery("SELECT * FROM numbers");
        if (resultSet.next()) {
            assertThat(resultSet.getInt(1)).isEqualTo(1);
            assertThat(resultSet.getInt(2)).isEqualTo(3);
        }

        //disconnect
        conn.close();
    }
}