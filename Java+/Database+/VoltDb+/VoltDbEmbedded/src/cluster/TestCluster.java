package cluster;

import org.voltcore.utils.PortGenerator;
import org.voltdb.BackendTarget;
import org.voltdb.ServerThread;
import org.voltdb.VoltDB.Configuration;
import org.voltdb.compiler.VoltProjectBuilder;
import org.voltdb.regressionsuites.LocalCluster;
import org.voltdb.utils.MiscUtils;

public class TestCluster {

    public static void runCluster() throws Exception {
        String pathToCatalog = Configuration.getPathToCatalogForTest("singlenode.jar");
        String pathToDeployment = Configuration.getPathToCatalogForTest("singlenode.xml");
        VoltProjectBuilder builder = makeBuilder();
        LocalCluster cluster = makeCluster(pathToCatalog, pathToDeployment, builder);
        cluster.startUp();
    }

    private static VoltProjectBuilder makeBuilder() throws Exception {
        // Create a VoltDB configuration.
//        Configuration config = new Configuration(new PortGenerator());
//        config.m_pathToCatalog = Configuration.getPathToCatalogForTest("singlenode.jar");
//        config.m_pathToDeployment = Configuration.getPathToCatalogForTest("singlenode.xml");

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
        builder.addPartitionInfo("PARTED1", "PARTVAL");
        builder.addPartitionInfo("PARTED2", "PARTVAL");
        builder.addPartitionInfo("PARTED3", "PARTVAL");
        builder.addPartitionInfo("PARTED4", "PARTVAL");

        // Register stored procedures.
        builder.addProcedures(
                TestProcedure.class
                // Add more procedures here.
        );

        // Compile the catalog using the configuration object catalog path.
        // Copy the deployment file from where the builder puts it to where
        // the configuration expects it.
//        boolean success = builder.compile(config.m_pathToCatalog, 2, 1, 0);
//        assert success;
//        MiscUtils.copyFile(builder.getPathToDeployment(), config.m_pathToDeployment);

        // Start a ServerThread as an in-process VoltDB back-end.
//        ServerThread server = new ServerThread(config);
//        server.start();
//        server.waitForInitialization();
        return builder;
    }

    private static LocalCluster makeCluster(String pathToCatalog, String pathToDeployment, VoltProjectBuilder builder) throws Exception {
        LocalCluster m_cluster = new LocalCluster(pathToCatalog, 2, 2, 1,
                BackendTarget.NATIVE_EE_JNI,
                LocalCluster.FailureState.ALL_RUNNING,
                false);
        m_cluster.setHasLocalServer(true);
        boolean success = m_cluster.compile(builder);
        assert (success);
        MiscUtils.copyFile(builder.getPathToDeployment(), pathToDeployment);
        return m_cluster;
    }
}