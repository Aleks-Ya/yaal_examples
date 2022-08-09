import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ApplicationConfiguration.class)
class H2SpringTest {
    @Autowired
    private DataSource ds;

    @Test
    void select() throws SQLException {
        var connection = ds.getConnection();
        var statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO cars (id) VALUES (5)");
        var rs = statement.executeQuery("SELECT id FROM cars");
        rs.next();
        assertThat(rs.getInt(1)).isEqualTo(5);
        statement.close();
        connection.close();
    }
}
