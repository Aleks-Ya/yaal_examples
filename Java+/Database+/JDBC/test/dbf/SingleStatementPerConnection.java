package dbf;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;

/**
 * МИФ: на один Connection можно создавать один Statement.
 * Если создать 2ой Statement, то под него MySQL создаст новый Connection, который невозможно закрыть, поэтому БД упадет.
 */
public class SingleStatementPerConnection {

    @Test
    public void test() throws SQLException {
        try (Connection conn = DriverManager.getConnection("jdbc:h2:mem:");
             Statement s1 = conn.createStatement();
             Statement s2 = conn.createStatement()) {

            assertEquals(s1.getConnection(), s2.getConnection());

        }
    }
}