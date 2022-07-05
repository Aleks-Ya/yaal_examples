import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Запуск встроенной БД без xml.
 */
class NoXml {
    private static EmbeddedDatabase db;

    @BeforeAll
    public static void beforeClass() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        db = builder.setType(EmbeddedDatabaseType.H2)
                .addScript("db/schema.sql")
                .addScript("db/test-data-h2.sql")
                .build();
    }

    @AfterAll
    public static void afterClass() {
        db.shutdown();
    }

    @Test
    void checkDataSource() throws SQLException {
        DataSource dataSource = db;
        Connection conn = dataSource.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM names WHERE id=2");
        assertThat(rs.next()).isTrue();
        assertThat(rs.getString("title")).isEqualTo("H2");
    }
}