package odbc;

import org.junit.jupiter.api.Test;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Don't work because JdbcOdbcDriver class is not found.
 */
class PrintTableNamesTest {

    @Test
    void test() throws ClassNotFoundException, SQLException {
        Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        var connString = "jdbc:odbc:Driver={Microsoft dBASE Driver (*.dbf)};DefaultDir=E:\\db";//DeafultDir indicates the location of the db
        var connection = DriverManager.getConnection(connString);

        var meta = connection.getMetaData();
        var tables = meta.getTables(null, null, null, null);
        while (tables.next()) {
            var schema = tables.getString(2);
            var name = tables.getString(3);
            System.out.println(schema + "-" + name);
        }
    }
}