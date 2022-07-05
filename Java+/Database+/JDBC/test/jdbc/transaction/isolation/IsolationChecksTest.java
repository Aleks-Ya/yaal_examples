package jdbc.transaction.isolation;

import jdbc.H2Helper;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static java.lang.String.format;
import static jdbc.H2Helper.rsToList;
import static org.assertj.core.api.Assertions.assertThat;

class IsolationChecksTest {
    private final String table = H2Helper.randomTableName();
    private final String createTable = H2Helper.createPersonTable(table);
    private final String insertJohn = H2Helper.insertJohn(table);
    private final String insertMary = H2Helper.insertMary(table);
    private final String selectAll = H2Helper.selectAll(table);

    private final Connection conn1;
    private final Statement st1;
    private final Statement st2;

    private IsolationChecksTest(Connection conn1, Connection conn2) throws SQLException {
        this.conn1 = conn1;
        conn1.setAutoCommit(false);
        conn2.setAutoCommit(false);
        st1 = conn1.createStatement();
        st2 = conn2.createStatement();
    }

    public static void assertUncommittedReadPossible(Connection conn1, Connection conn2) throws SQLException {
        new IsolationChecksTest(conn1, conn2).assertUncommittedReadPossible();
    }

    public static void assertUncommittedReadNotPossible(Connection conn1, Connection conn2) throws SQLException {
        new IsolationChecksTest(conn1, conn2).assertUncommittedReadNotPossible();
    }

    public static void assertNonRepeatableReadPossible(Connection conn1, Connection conn2) throws SQLException {
        new IsolationChecksTest(conn1, conn2).assertNonRepeatableReadPossible();
    }

    public static void assertNonRepeatableReadNotPossible(Connection conn1, Connection conn2) throws SQLException {
        new IsolationChecksTest(conn1, conn2).assertNonRepeatableReadNotPossible();
    }

    public static void assertPhantomReadPossible(Connection conn1, Connection conn2) throws SQLException {
        new IsolationChecksTest(conn1, conn2).assertPhantomReadPossible();
    }

    public static void assertPhantomReadNotPossible(Connection conn1, Connection conn2) throws SQLException {
        new IsolationChecksTest(conn1, conn2).assertPhantomReadNotPossible();
    }

    private void assertUncommittedReadPossible() throws SQLException {
        st1.executeUpdate(createTable);
        st1.executeUpdate(insertJohn);
        conn1.commit();
        assertThat(rsToList(st2.executeQuery(selectAll))).containsExactlyInAnyOrder("1 - John");

        st1.executeUpdate(insertMary);
        assertThat(rsToList(st2.executeQuery(selectAll))).containsExactlyInAnyOrder("1 - John", "2 - Mary");

        conn1.rollback();
        assertThat(rsToList(st2.executeQuery(selectAll))).containsExactlyInAnyOrder("1 - John");
    }

    private void assertUncommittedReadNotPossible() throws SQLException {
        st1.executeUpdate(createTable);
        st1.executeUpdate(insertJohn);
        conn1.commit();
        assertThat(rsToList(st2.executeQuery(selectAll))).containsExactlyInAnyOrder("1 - John");

        st1.executeUpdate(insertMary);
        assertThat(rsToList(st2.executeQuery(selectAll))).containsExactlyInAnyOrder("1 - John");

        conn1.rollback();
        assertThat(rsToList(st2.executeQuery(selectAll))).containsExactlyInAnyOrder("1 - John");
    }

    private void assertNonRepeatableReadPossible() throws SQLException {
        st1.executeUpdate(createTable);
        st1.executeUpdate(insertJohn);
        conn1.commit();

        assertThat(rsToList(st2.executeQuery(selectAll))).containsExactlyInAnyOrder("1 - John");

        st1.executeUpdate(format("UPDATE %s SET name='Mary' WHERE id=1", table));
        conn1.commit();

        assertThat(rsToList(st2.executeQuery(selectAll))).containsExactlyInAnyOrder("1 - Mary");
    }

    private void assertNonRepeatableReadNotPossible() throws SQLException {
        st1.executeUpdate(createTable);
        st1.executeUpdate(insertJohn);
        conn1.commit();

        assertThat(rsToList(st2.executeQuery(selectAll))).containsExactlyInAnyOrder("1 - John");

        st1.executeUpdate(format("UPDATE %s SET name='Mary' WHERE id=1", table));
        conn1.commit();

        assertThat(rsToList(st2.executeQuery(selectAll))).containsExactlyInAnyOrder("1 - John");
    }

    public void assertPhantomReadPossible() throws SQLException {
        st1.executeUpdate(createTable);
        st1.executeUpdate(insertJohn);
        conn1.commit();

        assertThat(rsToList(st2.executeQuery(selectAll))).containsExactlyInAnyOrder("1 - John");

        st1.executeUpdate(insertMary);
        conn1.commit();

        assertThat(rsToList(st2.executeQuery(selectAll))).containsExactlyInAnyOrder("1 - John", "2 - Mary");
    }

    public void assertPhantomReadNotPossible() throws SQLException {
        st1.executeUpdate(createTable);
        st1.executeUpdate(insertJohn);
        conn1.commit();

        assertThat(rsToList(st2.executeQuery(selectAll))).containsExactlyInAnyOrder("1 - John");

        st1.executeUpdate(insertMary);
        conn1.commit();

        assertThat(rsToList(st2.executeQuery(selectAll))).containsExactlyInAnyOrder("1 - John");
    }
}
