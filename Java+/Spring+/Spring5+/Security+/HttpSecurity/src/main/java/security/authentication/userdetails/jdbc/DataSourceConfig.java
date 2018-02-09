package security.authentication.userdetails.jdbc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import static security.ResourceUtils.resourceToString;

@Configuration
class DataSourceConfig {

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
        statement.addBatch(resourceToString("schema.sql", getClass()));
        statement.addBatch(resourceToString("users.sql", getClass()));
        statement.executeBatch();
        statement.close();
    }

}
