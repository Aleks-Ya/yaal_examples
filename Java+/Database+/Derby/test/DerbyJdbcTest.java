import org.junit.jupiter.api.Test;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

class DerbyJdbcTest {

    @Test
    void main() throws SQLException, ClassNotFoundException {
        //connect
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        var conn = DriverManager.getConnection(
                "jdbc:derby:memory:db_name;create=true", "", "");

        //insert
        var update = conn.createStatement();
        update.executeUpdate("CREATE TABLE numbers (numb INTEGER)");
        update.executeUpdate("INSERT INTO numbers VALUES (3)");

        //select
        var select = conn.createStatement();
        var resultSet = select.executeQuery("SELECT * FROM numbers");
        if (resultSet.next()) {
            assertThat(resultSet.getInt(1)).isEqualTo(3);
        }

        //disconnect
        conn.close();
    }
}