package server_thread.snapshot;

import org.voltcore.utils.PortGenerator;
import org.voltdb.ServerThread;
import org.voltdb.VoltDB.Configuration;
import org.voltdb.compiler.VoltProjectBuilder;
import org.voltdb.utils.MiscUtils;

public class TestServer {
    private static ServerThread server;

    public static int runServer() throws Exception {
        // Create a VoltDB configuration.
        Configuration config = new Configuration(new PortGenerator());
        config.m_pathToCatalog = Configuration.getPathToCatalogForTest("singlenode.jar");
        config.m_pathToDeployment = Configuration.getPathToCatalogForTest("singlenode.xml");

        // Specify the DDL and partitioning.
        VoltProjectBuilder builder = new VoltProjectBuilder();
        builder.addLiteralSchema("CREATE TABLE t1 (" +
                        "  id INTEGER DEFAULT '0' NOT NULL," +
                        "  number INTEGER NOT NULL," +
                        "  text VARCHAR(255) NOT NULL," +
//                    "  PRIMARY KEY (id)" +
                        "); "
//                    +
//                    "CREATE UNIQUE INDEX i1 ON t1 (id);"
        );
        builder.addPartitionInfo("t1", "number");

        // Compile the catalog using the configuration object catalog path.
        // Copy the deployment file from where the builder puts it to where
        // the configuration expects it.
        boolean success = builder.compile(config.m_pathToCatalog, 2, 1, 0);
        assert success;
        MiscUtils.copyFile(builder.getPathToDeployment(), config.m_pathToDeployment);

        // Start a ServerThread as an in-process VoltDB back-end.
        server = new ServerThread(config);
        server.start();
        server.waitForInitialization();
        return config.m_port;
    }

    public static void stopServer() {
        try {
            if (server != null) {
                server.shutdown();
                server.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}