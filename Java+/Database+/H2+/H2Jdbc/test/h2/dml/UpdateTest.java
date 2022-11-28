package h2.dml;

import org.junit.jupiter.api.Test;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class UpdateTest {

    @Test
    void update() throws SQLException {
        try (var conn = DriverManager.getConnection("jdbc:h2:mem:");
             var st = conn.createStatement()) {
            st.executeUpdate("CREATE TABLE person(id INTEGER, name VARCHAR)");
            st.executeUpdate("INSERT INTO person(id, name) VALUES (1, 'John'), (2, 'Mary')");
            st.executeUpdate("UPDATE person SET name='Mark' WHERE id=1");

            var rs = st.executeQuery("SELECT id, name FROM person");
            var persons = new ArrayList<String>();
            while (rs.next()) {
                persons.add(rs.getInt(1) + " - " + rs.getString(2));
            }
            assertThat(persons).containsExactlyInAnyOrder("1 - Mark", "2 - Mary");
        }
    }
}