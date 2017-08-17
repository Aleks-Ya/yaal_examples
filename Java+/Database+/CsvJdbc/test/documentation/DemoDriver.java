package documentation;

import java.io.File;
import java.net.URL;
import java.sql.*;

import org.junit.Test;
import org.relique.jdbc.csv.CsvDriver;

/**
 * Source: http://csvjdbc.sourceforge.net/
 */
public class DemoDriver {

    @Test
    public void main() throws Exception {
        // Load the driver.
        Class.forName("org.relique.jdbc.csv.CsvDriver");

        // Create a connection. The first command line parameter is
        // the directory containing the .csv files.
        // A single connection is thread-safe for use by several threads.
        String database = new File(DemoDriver.class.getResource("cities.csv").getFile()).getParent();
        Connection conn = DriverManager.getConnection("jdbc:relique:csv:" + database);

        // Create a Statement object to execute the query with.
        // A Statement is not thread-safe.
        Statement stmt = conn.createStatement();

        // Select the ID and NAME columns from sample.csv
        ResultSet results = stmt.executeQuery("SELECT ID,NAME,POPULATION FROM cities");

        // Dump out the results to a CSV file with the same format
        // using CsvJdbc helper function
        boolean append = true;
        CsvDriver.writeToCsv(results, System.out, append);

        // Clean up
        conn.close();
    }
}