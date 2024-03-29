package jdbc.transaction;

import jdbc.H2Helper;
import jdbc.H2Server;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static jdbc.H2Helper.rsToList;
import static org.assertj.core.api.Assertions.assertThat;

class CommitTransactionTest {

    @Test
    void autoCommit() throws SQLException {
        var h2 = H2Server.create();
        try (var conn1 = h2.openConn();
             var conn2 = h2.openConn()) {
            var st1 = conn1.createStatement();
            var table = H2Helper.randomTableName();
            st1.executeUpdate(H2Helper.createPersonTable(table));
            st1.executeUpdate(H2Helper.insertJohn(table));
            st1.executeUpdate(H2Helper.insertMary(table));

            var st2 = conn2.createStatement();
            var rs = st2.executeQuery(H2Helper.selectAll(table));
            assertThat(rsToList(rs)).containsExactlyInAnyOrder("1 - John", "2 - Mary");
        }
    }

    @Test
    void manualCommit() throws SQLException {
        var h2 = H2Server.create();
        try (var conn1 = h2.openConn();
             var conn2 = h2.openConn()) {
            conn1.setAutoCommit(false);
            var st1 = conn1.createStatement();
            var table = H2Helper.randomTableName();
            st1.executeUpdate(H2Helper.createPersonTable(table));
            st1.executeUpdate(H2Helper.insertJohn(table));
            st1.executeUpdate(H2Helper.insertMary(table));
            conn1.commit();

            var st2 = conn2.createStatement();
            var rs = st2.executeQuery(H2Helper.selectAll(table));
            assertThat(rsToList(rs)).containsExactlyInAnyOrder("1 - John", "2 - Mary");
        }
    }
}