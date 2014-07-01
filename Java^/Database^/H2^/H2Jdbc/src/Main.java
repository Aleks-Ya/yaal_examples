import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        //connect
        Class.forName("org.h2.Driver");
        Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");

        //insert
        Statement update = conn.createStatement();
        update.executeUpdate("DROP TABLE numbers");
        update.executeUpdate("CREATE TABLE numbers (numb INTEGER)");
        update.executeUpdate("INSERT INTO numbers VALUES (3)");

        //select
        Statement select = conn.createStatement();
        ResultSet resultSet = select.executeQuery("SELECT * FROM numbers");
        if (resultSet.next()) {
            System.out.println(resultSet.getInt(1));
        }

        //disconnect
        conn.close();
    }
}