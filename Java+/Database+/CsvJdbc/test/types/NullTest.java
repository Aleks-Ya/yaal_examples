package types;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.sql.*;
import java.text.ParseException;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * CSV standard doesn't support NULL values.
 * NULL, null, "NULL" will be treated as strings.
 */
public class NullTest {

    @Test
    public void test() throws ClassNotFoundException, SQLException, ParseException {
        Class.forName("org.relique.jdbc.csv.CsvDriver");

        Properties props = new Properties();

        String database = new File(NullTest.class.getResource("null.csv").getFile()).getParent();
        Connection conn = DriverManager.getConnection("jdbc:relique:csv:" + database, props);
        Statement stmt = conn.createStatement();

        ResultSet results = stmt.executeQuery("SELECT * FROM null");
        results.next();

        assertThat(results.getString("string"), equalTo("NULL"));

        conn.close();
    }
}