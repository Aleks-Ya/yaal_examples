package jdbc.transaction.isolation;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import static java.lang.String.format;
import static jdbc.H2Assert.rsToList;
import static org.assertj.core.api.Assertions.assertThat;

class IsolationChecks {
    private final String table = "Table_" + UUID.randomUUID().toString().replaceAll("-", "");
    private final String createTable = format("CREATE TABLE %s(id INTEGER, name VARCHAR)", table);
    private final String insertJohn = format("INSERT INTO %s(id, name) VALUES (1, 'John')", table);
    private final String insertMary = format("INSERT INTO %s(id, name) VALUES (2, 'Mary')", table);
    private final String selectAll = format("SELECT id, name FROM %s", table);

    private final Connection conn1;
    private final Statement st1;
    private final Statement st2;

    private IsolationChecks(Connection conn1, Connection conn2) throws SQLException {
        this.conn1 = conn1;
        conn1.setAutoCommit(false);
        conn2.setAutoCommit(false);
        st1 = conn1.createStatement();
        st2 = conn2.createStatement();
    }

    public static void assertUncommittedReadPossible(Connection conn1, Connection conn2) throws SQLException {
        new IsolationChecks(conn1, conn2).assertUncommittedReadPossible();
    }

    public static void assertUncommittedReadNotPossible(Connection conn1, Connection conn2) throws SQLException {
        new IsolationChecks(conn1, conn2).assertUncommittedReadNotPossible();
    }

    public static void assertNonRepeatableReadPossible(Connection conn1, Connection conn2) throws SQLException {
        new IsolationChecks(conn1, conn2).assertNonRepeatableReadPossible();
    }

    public static void assertNonRepeatableReadNotPossible(Connection conn1, Connection conn2) throws SQLException {
        new IsolationChecks(conn1, conn2).assertNonRepeatableReadNotPossible();
    }

    public static void assertPhantomReadPossible(Connection conn1, Connection conn2) throws SQLException {
        new IsolationChecks(conn1, conn2).assertPhantomReadPossible();
    }

    public static void assertPhantomReadNotPossible(Connection conn1, Connection conn2) throws SQLException {
        new IsolationChecks(conn1, conn2).assertPhantomReadNotPossible();
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
