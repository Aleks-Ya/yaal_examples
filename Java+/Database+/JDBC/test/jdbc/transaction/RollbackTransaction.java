package jdbc.transaction;

import jdbc.H2Server;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static jdbc.H2Assert.rsToList;
import static org.assertj.core.api.Assertions.assertThat;

class RollbackTransaction {

    @Test
    void rollback() throws SQLException {
        var h2 = H2Server.create();
        try (var conn1 = h2.openConn();
             var conn2 = h2.openConn()) {
            conn1.setAutoCommit(false);
            var st1 = conn1.createStatement();
            st1.executeUpdate("CREATE TABLE person(id INTEGER, name VARCHAR)");
            st1.executeUpdate("INSERT INTO person(id, name) VALUES (1, 'John')");
            conn1.rollback();
            st1.executeUpdate("INSERT INTO person(id, name) VALUES (2, 'Mary')");
            conn1.commit();

            var st2 = conn2.createStatement();
            var rs = st2.executeQuery("SELECT id, name FROM person");
            assertThat(rsToList(rs)).containsExactlyInAnyOrder("2 - Mary");
        }
    }
}