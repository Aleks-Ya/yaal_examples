package types;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.math.BigDecimal;
import java.sql.*;
import java.text.ParseException;
import java.util.Properties;
import java.util.TimeZone;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Parse different data types.
 */
public class DataTypesTest {

    @Test
    public void test() throws ClassNotFoundException, SQLException, ParseException {
        Class.forName("org.relique.jdbc.csv.CsvDriver");

        Properties props = new Properties();
        props.put("columnTypes", "String,Int,Date,Time,Timestamp,BigDecimal");
        props.put("timeZoneName", TimeZone.getDefault().getID());

        String database = new File(DataTypesTest.class.getResource("types.csv").getFile()).getParent();
        Connection conn = DriverManager.getConnection("jdbc:relique:csv:" + database, props);
        Statement stmt = conn.createStatement();

        ResultSet results = stmt.executeQuery("SELECT * FROM types");
        results.next();

        assertThat(results.getString("string"), equalTo("Glass"));
        assertThat(results.getInt("int"), equalTo(1));
        assertThat(results.getDate("date"), equalTo(Date.valueOf("2017-01-30")));
        assertThat(results.getTime("time"), equalTo(Time.valueOf("20:50:45")));
        assertThat(results.getTimestamp("timestamp"), equalTo(Timestamp.valueOf("2016-12-25 23:40:30")));
        assertThat(results.getBigDecimal("bigdecimal"), equalTo(new BigDecimal("100.50")));

        conn.close();
    }
}