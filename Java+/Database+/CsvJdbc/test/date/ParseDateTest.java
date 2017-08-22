package date;

import org.junit.Test;

import java.io.File;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Properties;

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
        props.put("columnTypes", "Int,Date,Time,Timestamp");

        String database = new File(ParseDateTest.class.getResource("standard_formats.csv").getFile()).getParent();
        Connection conn = DriverManager.getConnection("jdbc:relique:csv:" + database, props);
        Statement stmt = conn.createStatement();

        ResultSet results = stmt.executeQuery("SELECT * FROM standard_formats");

        while (results.next()) {
            assertThat(results.getInt(1), equalTo(1));
            assertThat(results.getDate(2), equalTo(new SimpleDateFormat("yyyy-MM-dd").parse("2017-01-30")));
            assertThat(results.getTime(3), equalTo(new SimpleDateFormat("HH:mm:ss").parse("20:50:45")));
            assertThat(results.getTimestamp(4), equalTo(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2016-12-25 23:40:30")));
        }

        conn.close();
    }

    /**
     * Parse Date, Time and Timestamp that wrote in custom formats.
     */
    @Test
    public void customFormats() throws ClassNotFoundException, SQLException {
        Class.forName("org.relique.jdbc.csv.CsvDriver");

        Properties props = new Properties();
        props.put("columnTypes", "Int,Date,Time,Timestamp");
        props.put("timestampFormat", "yyyy_MM_dd HH+mm+ss");
        props.put("timeFormat", "HH+mm+ss");
        props.put("dateFormat", "yyyy_MM_dd");

        String database = new File(ParseDateTest.class.getResource("custom_formats.csv").getFile()).getParent();
        Connection conn = DriverManager.getConnection("jdbc:relique:csv:" + database, props);
        Statement stmt = conn.createStatement();

        ResultSet results = stmt.executeQuery("SELECT * FROM custom_formats");

        while (results.next()) {
            System.out.println(results.getInt(1));
            System.out.println(results.getDate(2));
            System.out.println(results.getTime(3));
            System.out.println(results.getTimestamp(4));
        }

        conn.close();
    }
}