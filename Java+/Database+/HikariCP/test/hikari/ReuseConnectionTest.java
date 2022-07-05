package hikari;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

class ReuseConnectionTest {

    @Test
    void reuse() throws SQLException {
        var maximumPoolSize = 1;

        var config = new HikariConfig();
        config.setJdbcUrl(H2Helper.randomH2ServerUrl());
        config.setMaximumPoolSize(maximumPoolSize);

        try (var ds = new HikariDataSource(config)) {
            try (var conn = ds.getConnection()) {
                //insert
                var update = conn.createStatement();
                update.executeUpdate("CREATE TABLE numbers (numb INTEGER)");
                update.executeUpdate("INSERT INTO numbers VALUES (3)");

                //select1
                var select1 = conn.createStatement();
                var resultSet1 = select1.executeQuery("SELECT * FROM numbers");
                if (resultSet1.next()) {
                    assertThat(resultSet1.getInt(1)).isEqualTo(3);
                }
            }

            try (var conn = ds.getConnection()) {
                var select2 = conn.createStatement();
                var resultSet2 = select2.executeQuery("SELECT * FROM numbers");
                if (resultSet2.next()) {
                    assertThat(resultSet2.getInt(1)).isEqualTo(3);
                }
            }
        }
    }
}