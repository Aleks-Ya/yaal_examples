package jdbc.dbf;

import org.junit.jupiter.api.Test;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * МИФ: на один Connection можно создавать один Statement.
 * Если создать 2ой Statement, то под него MySQL создаст новый Connection, который невозможно закрыть, поэтому БД упадет.
 */
class SingleStatementPerConnection {

    @Test
    void test() throws SQLException {
        try (var conn = DriverManager.getConnection("jdbc:h2:mem:");
             var s1 = conn.createStatement();
             var s2 = conn.createStatement()) {
            assertThat(s1.getConnection()).isEqualTo(s2.getConnection());
        }
    }
}