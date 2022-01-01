package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static util.FileUtil.createAbsentTempFileDeleteOnExit;

public class H2Server {
    private final String url;
    private boolean serverStarted = false;

    private H2Server() {
        url = "jdbc:h2:" + createAbsentTempFileDeleteOnExit(H2Server.class.getSimpleName()).getAbsolutePath();
    }

    public static H2Server create() {
        return new H2Server();
    }

    public synchronized Connection openConn() throws SQLException {
        if (serverStarted) {
            return DriverManager.getConnection(url);
        } else {
            serverStarted = true;
            return DriverManager.getConnection(url + ";AUTO_SERVER=TRUE");
        }
    }
}