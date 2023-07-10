package h2.date_type.date_time;

import org.junit.jupiter.api.Test;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TimestampTest {

    @Test
    void timestamp() throws SQLException {
        try (var conn = DriverManager.getConnection("jdbc:h2:mem:");
             var st = conn.createStatement()) {
            st.executeUpdate("CREATE TABLE person(name VARCHAR, created TIMESTAMP)");
            st.executeUpdate("INSERT INTO person(name, created) VALUES ('John', '2007-12-03T10:15:30.00Z')");

            var rs = st.executeQuery("SELECT name, created FROM person");
            List<String> persons = new ArrayList<>();
            while (rs.next()) {
                var s = String.format("%s - '%s'", rs.getString(1), rs.getTimestamp(2));
                persons.add(s);
            }
            assertThat(persons).containsExactlyInAnyOrder("John - '2007-12-03 12:15:30.0'");
        }
    }

    @Test
    void timestamp_PreparedStatement() throws SQLException {
        try (var conn = DriverManager.getConnection("jdbc:h2:mem:");
             var st = conn.createStatement()) {
            st.executeUpdate("CREATE TABLE person(name VARCHAR, created TIMESTAMP)");
            var pst = conn.prepareStatement("INSERT INTO person(name, created) VALUES (?, ?)");
            var timestamp = Timestamp.valueOf("2007-12-03 12:15:30.0");
            pst.setString(1, "John");
            pst.setTimestamp(2, timestamp);
            pst.executeUpdate();

            var rs = st.executeQuery("SELECT name, created FROM person");
            List<String> persons = new ArrayList<>();
            while (rs.next()) {
                var s = String.format("%s - '%s'", rs.getString(1), rs.getTimestamp(2));
                persons.add(s);
            }
            assertThat(persons).containsExactlyInAnyOrder("John - '2007-12-03 12:15:30.0'");
        }
    }
}