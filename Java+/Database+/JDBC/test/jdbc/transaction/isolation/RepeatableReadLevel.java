package jdbc.transaction.isolation;

import jdbc.H2Server;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static java.sql.Connection.TRANSACTION_REPEATABLE_READ;
import static jdbc.transaction.isolation.IsolationChecks.committedReadPossible;
import static jdbc.transaction.isolation.IsolationChecks.nonRepeatableReadPossible;
import static jdbc.transaction.isolation.IsolationChecks.uncommittedReadPossible;
import static org.assertj.core.api.Assertions.assertThat;

class RepeatableReadLevel {

    @Test
    void repeatableRead() throws SQLException {
        var h2 = H2Server.create();
        try (var conn1 = h2.openConn();
             var conn2 = h2.openConn()) {
            conn2.setTransactionIsolation(TRANSACTION_REPEATABLE_READ);
            assertThat(uncommittedReadPossible(conn1, conn2)).isFalse();
            assertThat(committedReadPossible(conn1, conn2)).isTrue();
            assertThat(nonRepeatableReadPossible(conn1, conn2)).isTrue();
        }
    }
}