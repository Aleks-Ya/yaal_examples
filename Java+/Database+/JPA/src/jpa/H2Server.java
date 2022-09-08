package jpa;

import org.h2.tools.Server;
import util.FileUtil;

import java.sql.SQLException;

public class H2Server implements AutoCloseable {
    private final Server server;

    private H2Server(Server server) {
        this.server = server;
    }

    public static H2Server newServer() {
        try {
            return new H2Server(Server.createTcpServer("-ifNotExists").start());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String newDatabaseUrl() {
        var file = FileUtil.createAbsentTempFileDeleteOnExit(H2Server.class.getSimpleName()).getAbsolutePath();
        return String.format("jdbc:h2:tcp://localhost:%d/%s", server.getPort(), file);
    }

    @Override
    public void close() {
        server.stop();
    }
}
