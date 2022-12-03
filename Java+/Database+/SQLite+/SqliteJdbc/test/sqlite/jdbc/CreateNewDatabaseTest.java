package sqlite.jdbc;

import org.junit.jupiter.api.Test;
import util.FileUtil;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

class CreateNewDatabaseTest {
    @Test
    void create() throws SQLException {
        var dbFile = FileUtil.createAbsentTempFile(".sqlite");
        try (var connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile.getAbsolutePath());
             var statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE person(id INT, name VARCHAR)");
            statement.executeUpdate("INSERT INTO person(id, name) VALUES (1, 'John')");
            try (var rs = statement.executeQuery("SELECT id, name FROM person")) {
                rs.next();
                var id = rs.getInt("id");
                var name = rs.getString("name");
                assertThat(id).isEqualTo(1);
                assertThat(name).isEqualTo("John");
            }
        }
        assertThat(dbFile).exists();
    }

}