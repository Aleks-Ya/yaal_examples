package jdbc.transaction.isolation;

import jdbc.H2Server;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static java.sql.Connection.TRANSACTION_READ_COMMITTED;
import static java.sql.Connection.TRANSACTION_READ_UNCOMMITTED;
import static java.sql.Connection.TRANSACTION_REPEATABLE_READ;
import static java.sql.Connection.TRANSACTION_SERIALIZABLE;

class IsolationLevelTest {

    @Test
    void readUncommitted() throws SQLException {
        var h2 = H2Server.create();
        try (var conn1 = h2.openConn();
             var conn2 = h2.openConn()) {
            conn2.setTransactionIsolation(TRANSACTION_READ_UNCOMMITTED);
            IsolationChecksTest.assertUncommittedReadPossible(conn1, conn2);
            IsolationChecksTest.assertNonRepeatableReadPossible(conn1, conn2);
            IsolationChecksTest.assertPhantomReadPossible(conn1, conn2);
        }
    }

    @Test
    void readCommitted() throws SQLException {
        var h2 = H2Server.create();
        try (var conn1 = h2.openConn();
             var conn2 = h2.openConn()) {
            conn2.setTransactionIsolation(TRANSACTION_READ_COMMITTED);//default level
            IsolationChecksTest.assertUncommittedReadNotPossible(conn1, conn2);
            IsolationChecksTest.assertNonRepeatableReadPossible(conn1, conn2);
            IsolationChecksTest.assertPhantomReadPossible(conn1, conn2);
        }
    }

    @Test
    void repeatableRead() throws SQLException {
        var h2 = H2Server.create();
        try (var conn1 = h2.openConn();
             var conn2 = h2.openConn()) {
            conn2.setTransactionIsolation(TRANSACTION_REPEATABLE_READ);
            IsolationChecksTest.assertUncommittedReadNotPossible(conn1, conn2);
            IsolationChecksTest.assertNonRepeatableReadNotPossible(conn1, conn2);
//            IsolationChecks.assertPhantomReadPossible(conn1, conn2);
        }
    }

    @Test
    void phantomRead() throws SQLException {
        var h2 = H2Server.create();
        try (var conn1 = h2.openConn();
             var conn2 = h2.openConn()) {
            conn2.setTransactionIsolation(TRANSACTION_SERIALIZABLE);
            IsolationChecksTest.assertUncommittedReadNotPossible(conn1, conn2);
            IsolationChecksTest.assertNonRepeatableReadNotPossible(conn1, conn2);
            IsolationChecksTest.assertPhantomReadNotPossible(conn1, conn2);
        }
    }
}