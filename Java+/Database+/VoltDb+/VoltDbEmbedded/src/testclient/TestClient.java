package testclient;

import org.voltcore.utils.PortGenerator;
import org.voltdb.ServerThread;
import org.voltdb.VoltDB;
import org.voltdb.VoltDB.Configuration;
import org.voltdb.client.Client;
import org.voltdb.client.ClientFactory;
import org.voltdb.compiler.VoltProjectBuilder;
import org.voltdb.utils.MiscUtils;

public class TestClient {

    public static void main(String[] args) {
        ServerThread server = null;
        Client client = null;
        try {
            // Create a VoltDB configuration.
            VoltDB.Configuration config = new VoltDB.Configuration(new PortGenerator());
            config.m_pathToCatalog = Configuration.getPathToCatalogForTest("testclient.jar");
            config.m_pathToDeployment = Configuration.getPathToCatalogForTest("testclient.xml");

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

            // Register stored procedures.
            builder.addProcedures(new Class<?>[]{
                    TestProcedure.class
                    // Add more procedures here.
            });

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

            // Create a client and connect.
            client = ClientFactory.createClient();
            client.createConnection("localhost", config.m_port);

            // Perform the client actions.
            client.callProcedure("TestProcedure", 1, 111, "some text");
            // More actions go here.
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (client != null) {
                    client.close();
                }

                if (server != null) {
                    server.shutdown();
                    server.join();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}