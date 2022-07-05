package h2.connection.mode.embedded;

import org.h2.tools.Server;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.sql.DriverManager;
import java.sql.SQLException;

class ConnectToInMemoryH2Test {

    /**
     * Connect URL: jdbc:h2:tcp://localhost:9092/mem:dbname
     */
    @Test
    @Disabled("Sever works forever")
    void runServer() throws SQLException {
        var dbname = "dbname";
        var conn = DriverManager.getConnection("jdbc:h2:mem:" + dbname);

        var st = conn.createStatement();
        st.executeUpdate("DROP TABLE IF EXISTS numbers");
        st.executeUpdate("CREATE TABLE numbers (numb INTEGER)");
        st.executeUpdate("INSERT INTO numbers VALUES (3)");

        System.out.println("H2 database started, table created");
        System.out.println("DB name: " + dbname);

        var server = Server.createTcpServer("-tcpAllowOthers");
        server.start();
        System.out.println("Port: " + server.getPort());
        System.out.println("URL: " + server.getURL());
        System.out.println("Status: " + server.getStatus());

        while (true) ;
    }
}