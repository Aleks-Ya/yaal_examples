package security.authentication.userdetails.jdbc;

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
import java.util.UUID;
import java.util.stream.Collectors;

@Configuration
class DataSourceConfig {

    private static String resourceToString(String resource) throws IOException {
        Resource script = new ClassPathResource(resource, DataSourceConfig.class);
        return Files.readAllLines(script.getFile().toPath()).stream().collect(Collectors.joining("\n"));
    }

    @Bean
    DataSource dataSource() throws IOException, SQLException {
        String dbName = UUID.randomUUID().toString(); //for no clashes between tests
        String url = String.format("jdbc:h2:mem:%s;DB_CLOSE_DELAY=-1", dbName);
        DataSource ds = new DriverManagerDataSource(url);
        populateDb(ds);
        return ds;
    }

    private void populateDb(DataSource ds) throws SQLException, IOException {
        Statement statement = ds.getConnection().createStatement();
        statement.addBatch(resourceToString("schema.sql"));
        statement.addBatch(resourceToString("users.sql"));
        statement.executeBatch();
        statement.close();
    }

}
