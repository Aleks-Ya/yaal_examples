package date;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Properties;
import java.util.TimeZone;

import static org.assertj.core.api.Assertions.assertThat;

class ParseDateTest {

    /**
     * Parse Date, Time and Timestamp that wrote in standard formats.
     */
    @Test
    void standardFormats() throws ClassNotFoundException, SQLException, ParseException {
        Class.forName("org.relique.jdbc.csv.CsvDriver");

        var props = new Properties();
        props.put("columnTypes", "Date,Time,Timestamp");
        props.put("timeZoneName", TimeZone.getDefault().getID());

        var database = new File(ParseDateTest.class.getResource("standard_formats.csv").getFile()).getParent();
        var conn = DriverManager.getConnection("jdbc:relique:csv:" + database, props);
        var stmt = conn.createStatement();

        var results = stmt.executeQuery("SELECT * FROM standard_formats");
        results.next();

        assertThat(results.getDate("date")).isEqualTo(Date.valueOf("2017-01-30"));
        assertThat(results.getTime("time")).isEqualTo(Time.valueOf("20:50:45"));
        assertThat(results.getTimestamp("timestamp")).isEqualTo(Timestamp.valueOf("2016-12-25 23:40:30"));

        conn.close();
    }

    /**
     * Parse Date, Time and Timestamp that wrote in custom formats.
     */
    @Test
    void customFormats() throws ClassNotFoundException, SQLException {
        Class.forName("org.relique.jdbc.csv.CsvDriver");

        var props = new Properties();
        props.put("columnTypes", "Date,Time,Timestamp");
        props.put("timeZoneName", TimeZone.getDefault().getID());
        props.put("timestampFormat", "yyyy_MM_dd HH+mm+ss");
        props.put("timeFormat", "HH+mm+ss");
        props.put("dateFormat", "yyyy_MM_dd");

        var database = new File(ParseDateTest.class.getResource("custom_formats.csv").getFile()).getParent();
        var conn = DriverManager.getConnection("jdbc:relique:csv:" + database, props);
        var stmt = conn.createStatement();

        var results = stmt.executeQuery("SELECT * FROM custom_formats");
        results.next();

        assertThat(results.getDate("date")).isEqualTo(Date.valueOf("2017-01-30"));
        assertThat(results.getTime("time")).isEqualTo(Time.valueOf("20:50:45"));
        assertThat(results.getTimestamp("timestamp")).isEqualTo(Timestamp.valueOf("2016-12-25 23:40:30"));

        conn.close();
    }
}