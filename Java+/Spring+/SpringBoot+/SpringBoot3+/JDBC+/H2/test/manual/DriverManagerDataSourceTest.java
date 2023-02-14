package manual;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DriverManagerDataSourceTest.Config.class)
class DriverManagerDataSourceTest {
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

    @Configuration
    static class Config {

        @Bean
        DataSource dataSource() throws SQLException, IOException {
            var ds = new DriverManagerDataSource("jdbc:h2:mem:test1");
            populateDb(ds);
            return ds;
        }

        private void populateDb(DataSource ds) throws SQLException, IOException {
            var statement = ds.getConnection().createStatement();
            var script = new ClassPathResource("schema-h2.sql");
            var strings = Files.readAllLines(script.getFile().toPath());
            for (var string : strings) {
                statement.addBatch(string);
            }
            statement.executeBatch();
            statement.close();
        }
    }

}
