package sqlite.jdbc;

import org.junit.jupiter.api.Test;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

class SpecialSymbolInTextTest {
    @Test
    void singleQuote() throws SQLException {
        try (var connection = DriverManager.getConnection("jdbc:sqlite::memory:");
             var statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE person(id INT, name VARCHAR)");
            statement.executeUpdate("INSERT INTO person(id, name) VALUES (1, 'John''s')");
            try (var rs = statement.executeQuery("SELECT id, name FROM person")) {
                rs.next();
                var id = rs.getInt("id");
                var name = rs.getString("name");
                assertThat(id).isEqualTo(1);
                assertThat(name).isEqualTo("John's");
            }
        }
    }
}