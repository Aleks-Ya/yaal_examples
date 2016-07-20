package sql_from_file;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

/**
 * Весь файл с SQL-скриптом выполняется за один запрос к БД.
 */
public class WholeSqlFile {

    @Test
    public void test() throws SQLException, ClassNotFoundException, IOException {
        StringBuilder query = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(WholeSqlFile.class.getResourceAsStream("big.sql")))) {
            String line;
            while ((line = reader.readLine()) != null) {
                query.append(line);
                query.append("\n");
            }
        }
        String queryStr = query.toString().replace("\uFEFF", "");//Удаляем Byte Order Mark

        Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "", "");
        Statement statement = conn.createStatement();
        statement.executeUpdate(queryStr);

        ResultSet rs = statement.executeQuery("SELECT * FROM execute_sql");
        while (rs.next()) {
            System.out.printf("%d - %s\n", rs.getInt(1), rs.getString(2));
        }

        conn.close();
    }
}