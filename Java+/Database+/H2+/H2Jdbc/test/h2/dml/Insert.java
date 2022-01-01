package h2.dml;

import org.junit.jupiter.api.Test;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Insert {

    @Test
    void insertSeveralValues() throws SQLException {
        try (var conn = DriverManager.getConnection("jdbc:h2:mem:");
             var st = conn.createStatement()) {
            st.executeUpdate("CREATE TABLE person(id INTEGER, name VARCHAR)");
            st.executeUpdate("INSERT INTO person(id, name) VALUES (1, 'John'), (2, 'Mary')");

            var rs = st.executeQuery("SELECT id, name FROM person");
            List<String> persons = new ArrayList<>();
            while (rs.next()) {
                persons.add(rs.getInt(1) + " - " + rs.getString(2));
            }
            assertThat(persons).containsExactlyInAnyOrder("1 - John", "2 - Mary");
        }
    }
}