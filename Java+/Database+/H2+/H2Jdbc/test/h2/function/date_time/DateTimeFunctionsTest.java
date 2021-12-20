package h2.function.date_time;

import org.junit.jupiter.api.Test;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

class DateTimeFunctionsTest {

    private static void executeFunction(Statement st, String function) throws SQLException {
        var rs = st.executeQuery("select " + function);
        rs.next();
        var value = rs.getObject(1);
        System.out.println(function + ": '" + value + "'");
    }

    @Test
    void printFunctionResults() throws SQLException {
        try (var conn = DriverManager.getConnection("jdbc:h2:mem:");
             var st = conn.createStatement()) {
            executeFunction(st, "CURRENT_TIMESTAMP");
            executeFunction(st, "CURRENT_TIMESTAMP(9)");
            executeFunction(st, "CURRENT_DATE");
            executeFunction(st, "CURRENT_TIME");
            executeFunction(st, "LOCALTIME");
            executeFunction(st, "LOCALTIMESTAMP");
        }
    }
}