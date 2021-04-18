package date;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.sql.*;
import java.text.ParseException;
import java.util.Properties;
import java.util.TimeZone;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ParseDateTest {

    /**
     * Parse Date, Time and Timestamp that wrote in standard formats.
     */
    @Test
    public void standardFormats() throws ClassNotFoundException, SQLException, ParseException {
        Class.forName("org.relique.jdbc.csv.CsvDriver");

        Properties props = new Properties();
        props.put("columnTypes", "Date,Time,Timestamp");
        props.put("timeZoneName", TimeZone.getDefault().getID());

        String database = new File(ParseDateTest.class.getResource("standard_formats.csv").getFile()).getParent();
        Connection conn = DriverManager.getConnection("jdbc:relique:csv:" + database, props);
        Statement stmt = conn.createStatement();

        ResultSet results = stmt.executeQuery("SELECT * FROM standard_formats");
        results.next();

        assertThat(results.getDate("date"), equalTo(Date.valueOf("2017-01-30")));
        assertThat(results.getTime("time"), equalTo(Time.valueOf("20:50:45")));
        assertThat(results.getTimestamp("timestamp"), equalTo(Timestamp.valueOf("2016-12-25 23:40:30")));

        conn.close();
    }

    /**
     * Parse Date, Time and Timestamp that wrote in custom formats.
     */
    @Test
    public void customFormats() throws ClassNotFoundException, SQLException {
        Class.forName("org.relique.jdbc.csv.CsvDriver");

        Properties props = new Properties();
        props.put("columnTypes", "Date,Time,Timestamp");
        props.put("timeZoneName", TimeZone.getDefault().getID());
        props.put("timestampFormat", "yyyy_MM_dd HH+mm+ss");
        props.put("timeFormat", "HH+mm+ss");
        props.put("dateFormat", "yyyy_MM_dd");

        String database = new File(ParseDateTest.class.getResource("custom_formats.csv").getFile()).getParent();
        Connection conn = DriverManager.getConnection("jdbc:relique:csv:" + database, props);
        Statement stmt = conn.createStatement();

        ResultSet results = stmt.executeQuery("SELECT * FROM custom_formats");
        results.next();

        assertThat(results.getDate("date"), equalTo(Date.valueOf("2017-01-30")));
        assertThat(results.getTime("time"), equalTo(Time.valueOf("20:50:45")));
        assertThat(results.getTimestamp("timestamp"), equalTo(Timestamp.valueOf("2016-12-25 23:40:30")));

        conn.close();
    }
}