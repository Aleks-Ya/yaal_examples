package connection.mode.embedded;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class CredentialsTest {

    /**
     * User you are connecting is created automatically.
     */
    @Test
    public void autoCreatedUser() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:mem:", "my_user", "my_pass");

        Statement select = conn.createStatement();
        ResultSet resultSet = select.executeQuery("SELECT * FROM INFORMATION_SCHEMA.USERS");
        if (resultSet.next()) {
            assertThat(resultSet.getString("NAME"), equalTo("MY_USER"));
        }

        conn.close();
    }
}