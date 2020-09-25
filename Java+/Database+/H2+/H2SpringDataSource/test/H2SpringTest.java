import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationConfiguration.class)
@ActiveProfiles("aaa")
public class H2SpringTest {
    @Autowired
    private DataSource ds;

    @Test
    public void name() throws SQLException {
        Connection connection = ds.getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO cars (id) VALUES (5)");
        ResultSet rs = statement.executeQuery("SELECT id FROM cars");
        rs.next();
        assertThat(rs.getInt(1), equalTo(5));
        statement.close();
        connection.close();
    }
}
