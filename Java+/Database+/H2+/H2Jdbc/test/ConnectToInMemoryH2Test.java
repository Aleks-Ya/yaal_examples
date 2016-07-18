import org.h2.tools.Server;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectToInMemoryH2Test {

    /**
     * Подключаться к БД по jdbc:h2:tcp://localhost:9092/mem:dbname
     */
    @Test
    public void runServer() throws SQLException, ClassNotFoundException {
        Class.forName("org.h2.Driver");
        String dbname = "dbname";
        Connection conn = DriverManager.getConnection("jdbc:h2:mem:" + dbname);

        Statement update = conn.createStatement();
        update.executeUpdate("DROP TABLE IF EXISTS numbers");
        update.executeUpdate("CREATE TABLE numbers (numb INTEGER)");
        update.executeUpdate("INSERT INTO numbers VALUES (3)");

        System.out.println("H2 database started, table created");
        System.out.println("DB name: " + dbname);

        Server server = Server.createTcpServer("-tcpAllowOthers");
        server.start();
        System.out.println("Port: " + server.getPort());
        System.out.println("URL: " + server.getURL());
        System.out.println("Status: " + server.getStatus());

        while (true) ;
    }
}