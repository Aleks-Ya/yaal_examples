package hikari;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

class HikariInitTest {

    @Test
    void initWithJdbcUrl() throws SQLException {
        var maximumPoolSize = 5;

        var config = new HikariConfig();
        config.setJdbcUrl(H2Helper.randomH2ServerUrl());
        config.setMaximumPoolSize(maximumPoolSize);

        try (var ds = new HikariDataSource(config);
             var conn1 = ds.getConnection();
             var conn2 = ds.getConnection()) {
            assertThat(ds.getMaximumPoolSize()).isEqualTo(maximumPoolSize);

            //insert
            var update = conn1.createStatement();
            update.executeUpdate("CREATE TABLE numbers (numb INTEGER)");
            update.executeUpdate("INSERT INTO numbers VALUES (3)");

            //select1
            var select1 = conn1.createStatement();
            var resultSet1 = select1.executeQuery("SELECT * FROM numbers");
            if (resultSet1.next()) {
                assertThat(resultSet1.getInt(1)).isEqualTo(3);
            }

            //select2
            var select2 = conn2.createStatement();
            var resultSet2 = select2.executeQuery("SELECT * FROM numbers");
            if (resultSet2.next()) {
                assertThat(resultSet2.getInt(1)).isEqualTo(3);
            }
        }
    }
}