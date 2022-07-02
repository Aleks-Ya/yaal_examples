package security.authentication.userdetails.jdbc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import util.ResourceUtil;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

@Configuration
class DataSourceConfig {

    @Bean
    DataSource dataSource() throws IOException, SQLException {
        var dbName = UUID.randomUUID().toString(); //for no clashes between tests
        var url = String.format("jdbc:h2:mem:%s;DB_CLOSE_DELAY=-1", dbName);
        DataSource ds = new DriverManagerDataSource(url);
        populateDb(ds);
        return ds;
    }

    private void populateDb(DataSource ds) throws SQLException {
        var statement = ds.getConnection().createStatement();
        statement.addBatch(ResourceUtil.resourceToString(getClass(), "schema.sql"));
        statement.addBatch(ResourceUtil.resourceToString(getClass(), "users.sql"));
        statement.executeBatch();
        statement.close();
    }

}
