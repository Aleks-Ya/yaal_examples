package jdbc.transaction.isolation;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

import static java.lang.String.format;
import static jdbc.H2Assert.rsToList;
import static org.assertj.core.api.Assertions.assertThat;

class IsolationChecks {
    public static boolean uncommittedReadPossible(Connection conn1, Connection conn2) throws SQLException {
        try {
            var table = "Table_" + UUID.randomUUID().toString().replaceAll("-", "");
            conn1.setAutoCommit(false);
            var st1 = conn1.createStatement();
            st1.executeUpdate(format("CREATE TABLE %s(id INTEGER, name VARCHAR)", table));
            st1.executeUpdate(format("INSERT INTO %s(id, name) VALUES (1, 'John'), (2, 'Mary')", table));

            var st2 = conn2.createStatement();
            var rs = st2.executeQuery(format("SELECT id, name FROM %s", table));
            assertThat(rsToList(rs)).containsExactlyInAnyOrder("1 - John", "2 - Mary");

            conn1.rollback();

            var rs2 = st2.executeQuery(format("SELECT id, name FROM %s", table));
            assertThat(rsToList(rs2)).isEmpty();
            return true;
        } catch (AssertionError e) {
            return false;
        }
    }

    public static boolean committedReadPossible(Connection conn1, Connection conn2) throws SQLException {
        try {
            var table = "Table_" + UUID.randomUUID().toString().replaceAll("-", "");
            conn1.setAutoCommit(false);
            var st1 = conn1.createStatement();
            st1.executeUpdate(format("CREATE TABLE %s(id INTEGER, name VARCHAR)", table));
            st1.executeUpdate(format("INSERT INTO %s(id, name) VALUES (1, 'John'), (2, 'Mary')", table));
            conn1.commit();
            st1.executeUpdate(format("INSERT INTO %s(id, name) VALUES (3, 'Mark')", table));


            var st2 = conn2.createStatement();
            assertThat(rsToList(st2.executeQuery(format("SELECT id, name FROM %s", table))))
                    .containsExactlyInAnyOrder("1 - John", "2 - Mary");

            conn1.commit();
            assertThat(rsToList(st2.executeQuery(format("SELECT id, name FROM %s", table))))
                    .containsExactlyInAnyOrder("1 - John", "2 - Mary", "3 - Mark");
            return true;
        } catch (AssertionError e) {
            return false;
        }
    }

    public static boolean nonRepeatableReadPossible(Connection conn1, Connection conn2) throws SQLException {
        try {
            var table = "Table_" + UUID.randomUUID().toString().replaceAll("-", "");
            conn1.setAutoCommit(false);
            var st1 = conn1.createStatement();
            var st2 = conn2.createStatement();

            st1.executeUpdate(format("CREATE TABLE %s(id INTEGER, name VARCHAR)", table));
            st1.executeUpdate(format("INSERT INTO %s(id, name) VALUES (1, 'John')", table));
            conn1.commit();

            assertThat(rsToList(st2.executeQuery(format("SELECT id, name FROM %s", table))))
                    .containsExactlyInAnyOrder("1 - John");

            st1.executeUpdate(format("INSERT INTO %s(id, name) VALUES (2, 'Mary')", table));
            conn1.commit();

            assertThat(rsToList(st2.executeQuery(format("SELECT id, name FROM %s", table))))
                    .containsExactlyInAnyOrder("1 - John", "2 - Mary");
            return true;
        } catch (AssertionError e) {
            return false;
        }
    }
}
