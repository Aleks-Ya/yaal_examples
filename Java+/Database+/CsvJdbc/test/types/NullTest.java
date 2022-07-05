package types;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * CSV standard doesn't support NULL values.
 * NULL, null, "NULL" will be treated as strings.
 */
class NullTest {

    @Test
    void test() throws ClassNotFoundException, SQLException, ParseException {
        Class.forName("org.relique.jdbc.csv.CsvDriver");

        var props = new Properties();

        var database = new File(NullTest.class.getResource("null.csv").getFile()).getParent();
        var conn = DriverManager.getConnection("jdbc:relique:csv:" + database, props);
        var stmt = conn.createStatement();

        var results = stmt.executeQuery("SELECT * FROM null");
        results.next();

        assertThat(results.getString("string")).isEqualTo("NULL");

        conn.close();
    }
}