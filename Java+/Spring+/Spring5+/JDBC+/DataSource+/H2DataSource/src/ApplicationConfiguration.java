import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;

/**
 * @author Aleksey Yablokov
 */
@Configuration
class ApplicationConfiguration {
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
