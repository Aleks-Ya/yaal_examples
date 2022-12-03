package sqlite.jdbc;

import org.junit.jupiter.api.Test;
import util.ResourceUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

class InitFromSqlFileTest {
    private static void populateDatabase(Connection connection) throws SQLException {
        var initScript = ResourceUtil.resourceToString("sqlite/jdbc/InitFromSqlFileTest.sql");
        var statements = initScript.split(";\n");
        var statement = connection.createStatement();
        for (var st : statements) {
            if (!st.isBlank()) {
                statement.addBatch(st);
            }
        }
        statement.executeBatch();
    }

    private static void assertDatabase(Connection connection) throws SQLException {
        var statement = connection.createStatement();
        try (var rs = statement.executeQuery("SELECT id, name FROM person")) {
            rs.next();
            var id = rs.getInt("id");
            var name = rs.getString("name");
            assertThat(id).isEqualTo(1);
            assertThat(name).isEqualTo("John");
        }
    }

    @Test
    void init() throws SQLException {
        try (var connection = DriverManager.getConnection("jdbc:sqlite::memory:")) {
            populateDatabase(connection);
            assertDatabase(connection);
        }
    }
}