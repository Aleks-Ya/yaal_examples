package server_thread;

import org.apache.commons.io.FileUtils;
import org.voltcore.utils.PortGenerator;
import org.voltdb.ServerThread;
import org.voltdb.VoltDB.Configuration;
import org.voltdb.compiler.VoltProjectBuilder;
import org.voltdb.utils.MiscUtils;

import java.io.File;
import java.net.URL;

/**
 * Helper для удобного запуска сервера VoltDb из java-кода.
 */
public class ServerThreadHelper {
    private static ServerThread server;

    /**
     * SQL schema in resource file.
     */
    public static int runServer(URL resource) throws Exception {
        return runServer(FileUtils.readFileToString(new File(resource.getFile())));
    }

    /**
     * Use "default_schema.sql"
     */
    public static int runServer() throws Exception {
        return runServer(ServerThreadHelper.class.getResource("default_schema.sql"));
    }

    /**
     * SQL schema as string.
     */
    public static int runServer(String sqlSchema) throws Exception {
        // Create a VoltDB configuration.
        Configuration config = new Configuration(new PortGenerator());
        config.m_pathToCatalog = Configuration.getPathToCatalogForTest("server_thread_helper.jar");
        config.m_pathToDeployment = Configuration.getPathToCatalogForTest("server_thread_helper.xml");

        // Specify the DDL and partitioning.
        VoltProjectBuilder builder = new VoltProjectBuilder();
        builder.addLiteralSchema(sqlSchema);
        builder.addPartitionInfo("t1", "number");

        // Register stored procedures.
        builder.addProcedures(
                TestProcedure.class
                // Add more procedures here.
        );

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