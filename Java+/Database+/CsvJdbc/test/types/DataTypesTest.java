package types;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Properties;
import java.util.TimeZone;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Parse different data types.
 */
class DataTypesTest {

    @Test
    void test() throws ClassNotFoundException, SQLException, ParseException {
        Class.forName("org.relique.jdbc.csv.CsvDriver");

        var props = new Properties();
        props.put("columnTypes", "String,Int,Date,Time,Timestamp,BigDecimal");
        props.put("timeZoneName", TimeZone.getDefault().getID());

        var database = new File(DataTypesTest.class.getResource("types.csv").getFile()).getParent();
        var conn = DriverManager.getConnection("jdbc:relique:csv:" + database, props);
        var stmt = conn.createStatement();

        var results = stmt.executeQuery("SELECT * FROM types");
        results.next();

        assertThat(results.getString("string")).isEqualTo("Glass");
        assertThat(results.getInt("int")).isEqualTo(1);
        assertThat(results.getDate("date")).isEqualTo(Date.valueOf("2017-01-30"));
        assertThat(results.getTime("time")).isEqualTo(Time.valueOf("20:50:45"));
        assertThat(results.getTimestamp("timestamp")).isEqualTo(Timestamp.valueOf("2016-12-25 23:40:30"));
        assertThat(results.getBigDecimal("bigdecimal")).isEqualTo(new BigDecimal("100.50"));

        conn.close();
    }
}