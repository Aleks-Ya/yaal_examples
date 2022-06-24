package hikari;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ReuseConnection {

    @Test
    void reuse() throws SQLException {
        int maximumPoolSize = 1;

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(H2Helper.randomH2ServerUrl());
        config.setMaximumPoolSize(maximumPoolSize);

        try (HikariDataSource ds = new HikariDataSource(config)) {
            try (Connection conn = ds.getConnection()) {
                //insert
                Statement update = conn.createStatement();
                update.executeUpdate("CREATE TABLE numbers (numb INTEGER)");
                update.executeUpdate("INSERT INTO numbers VALUES (3)");

                //select1
                Statement select1 = conn.createStatement();
                ResultSet resultSet1 = select1.executeQuery("SELECT * FROM numbers");
                if (resultSet1.next()) {
                    assertThat(resultSet1.getInt(1), equalTo(3));
                }
            }

            try (Connection conn = ds.getConnection()) {
                Statement select2 = conn.createStatement();
                ResultSet resultSet2 = select2.executeQuery("SELECT * FROM numbers");
                if (resultSet2.next()) {
                    assertThat(resultSet2.getInt(1), equalTo(3));
                }
            }
        }
    }
}