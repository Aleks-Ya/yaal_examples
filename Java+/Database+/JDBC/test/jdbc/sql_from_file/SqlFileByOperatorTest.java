package jdbc.sql_from_file;

import org.junit.jupiter.api.Test;
import util.ResourceUtil;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * SQL-скрипт выполняется по одной команде (парсинг по ";").
 */
class SqlFileByOperatorTest {

    @Test
    void test() throws SQLException {
        var query = ResourceUtil.resourceToString("jdbc/sql_from_file/big.sql");
        var queryStr = query.replace("\uFEFF", "");//Удаляем Byte Order Mark

        try (var conn = DriverManager.getConnection("jdbc:h2:~/test", "", "");
             var statement = conn.createStatement()) {
            for (var command : queryStr.split(";")) {
                statement.executeUpdate(command);
            }
            var rs = statement.executeQuery("SELECT * FROM execute_sql");
            while (rs.next()) {
                System.out.printf("%d - %s\n", rs.getInt(1), rs.getString(2));
            }
        }
    }
}