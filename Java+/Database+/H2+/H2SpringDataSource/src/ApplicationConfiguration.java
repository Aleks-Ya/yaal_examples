import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author Aleksey Yablokov
 */
@Configuration
public class ApplicationConfiguration {

    @Bean
    DataSource dataSource() throws SQLException, IOException {
        DataSource ds = new DriverManagerDataSource("jdbc:h2:mem:test1");
        populateDb(ds);
        return ds;
    }

    private void populateDb(DataSource ds) throws SQLException, IOException {
        Statement statement = ds.getConnection().createStatement();
        Resource script = new ClassPathResource("schema-h2.sql");
        List<String> strings = Files.readAllLines(script.getFile().toPath());
        for (String string : strings) {
            statement.addBatch(string);
        }
        statement.executeBatch();
        statement.close();
    }
}
