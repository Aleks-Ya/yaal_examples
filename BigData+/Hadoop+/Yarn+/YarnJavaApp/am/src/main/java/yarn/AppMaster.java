package yarn;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.yarn.api.ApplicationConstants;
import org.apache.hadoop.yarn.api.records.Container;
import org.apache.hadoop.yarn.api.records.ContainerLaunchContext;
import org.apache.hadoop.yarn.api.records.ContainerStatus;
import org.apache.hadoop.yarn.api.records.FinalApplicationStatus;
import org.apache.hadoop.yarn.api.records.LocalResource;
import org.apache.hadoop.yarn.api.records.NodeReport;
import org.apache.hadoop.yarn.api.records.Priority;
import org.apache.hadoop.yarn.api.records.Resource;
import org.apache.hadoop.yarn.api.records.UpdatedContainer;
import org.apache.hadoop.yarn.client.api.AMRMClient.ContainerRequest;
import org.apache.hadoop.yarn.client.api.NMClient;
import org.apache.hadoop.yarn.client.api.async.AMRMClientAsync;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import org.apache.hadoop.yarn.util.Records;
import org.joda.time.DateTime;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static yarn.CommonConstants.CONTAINER_JAR_PATH;
import static yarn.CommonConstants.PARAM_FROM_CLIENT_TO_CONTAINER_NAME;

public class AppMaster extends AMRMClientAsync.AbstractCallbackHandler {
    private YarnConfiguration conf = new YarnConfiguration();
    private NMClient nmClient;
    private int containerCount = 3;

    public static void main(String[] args) throws IOException {
        System.out.println("AppMaster: Environment variables: \n" + System.getenv());
        System.out.println("AppMaster: Java properties: \n" + System.getProperties());

        String pwdDirs = System.getenv("PWD");
        System.out.println("pwdDirs: " + pwdDirs);
        System.out.println("Files in pwdDirs: " + Files.list(Paths.get(pwdDirs)).map(Path::toAbsolutePath).collect(Collectors.toList()));

        String localDirs = System.getenv("LOCAL_DIRS");
        System.out.println("localDirs: " + localDirs);
        System.out.println("Files in LOCAL_DIRS: " + Files.list(Paths.get(localDirs)).map(Path::toAbsolutePath).collect(Collectors.toList()));

        String defaultContainerExecutor = pwdDirs + "/default_container_executor.sh";
        System.out.println("defaultContainerExecutor: " + defaultContainerExecutor);
        System.out.println("default_container_executor.sh content:\n" + Files.readAllLines(Paths.get(defaultContainerExecutor)));

        String defaultContainerExecutorSession = pwdDirs + "/default_container_executor_session.sh";
        System.out.println("defaultContainerExecutorSession: " + defaultContainerExecutorSession);
        System.out.println("defaultContainerExecutorSession content:\n" + Files.readAllLines(Paths.get(defaultContainerExecutorSession)));

        String launchContainer = pwdDirs + "/launch_container.sh";
        System.out.println("launchContainer: " + launchContainer);
        System.out.println("launchContainer content:\n" + Files.readAllLines(Paths.get(launchContainer)));

        try {
            new AppMaster().run();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void run() throws Exception {
        // Use classes from dependency
        DateTime dateTime = new DateTime(2015, 3, 25, 15, 45, 20);
        System.out.println("dateTime: " + dateTime);


        conf = new YarnConfiguration();

        // Create NM Client
        nmClient = NMClient.createNMClient();
        nmClient.init(conf);
        nmClient.start();

        // Create AM - RM Client
        AMRMClientAsync<ContainerRequest> rmClient = AMRMClientAsync.createAMRMClientAsync(1000, this);
        rmClient.init(conf);
        rmClient.start();

        // Register with RM
        rmClient.registerApplicationMaster("", 0, "");
        System.out.println("AppMaster: Registered");

        // Priority for worker containers - priorities are intra-application
        Priority priority = Records.newRecord(Priority.class);
        priority.setPriority(0);

        // Resource requirements for worker containers
        Resource capability = Records.newRecord(Resource.class);
        capability.setMemorySize(128);
        capability.setVirtualCores(1);

        // Request Containers from RM
        System.out.println("AppMaster: Requesting " + containerCount + " Containers");
        for (int i = 0; i < containerCount; ++i) {
            rmClient.addContainerRequest(new ContainerRequest(capability, null, null, priority));
        }

        while (!containersFinished()) {
            Thread.sleep(100);
        }

        System.out.println("AppMaster: Unregistered");
        rmClient.unregisterApplicationMaster(FinalApplicationStatus.SUCCEEDED, "", "");
    }

    private boolean containersFinished() {
        return containerCount == 0;
    }

    @Override
    public void onContainersAllocated(List<Container> containers) {
        for (Container container : containers) {
            try {
                nmClient.startContainer(container, initContainer());
                System.err.println("AppMaster: Container launched " + container.getId());
            } catch (Exception ex) {
                System.err.println("AppMaster: Container not launched " + container.getId());
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void onContainersUpdated(List<UpdatedContainer> containers) {
        for (UpdatedContainer container : containers) {
            try {
                System.err.println("AppMaster: Container updated " + container.getContainer().getId());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private ContainerLaunchContext initContainer() {
        try {
            // Create Container Context
            ContainerLaunchContext cCLC = Records.newRecord(ContainerLaunchContext.class);
            cCLC.setCommands(Collections.singletonList("$JAVA_HOME/bin/java"
                    + " -Xmx256M"
                    + " yarn.Container"
                    + " 1>" + ApplicationConstants.LOG_DIR_EXPANSION_VAR + "/stdout"
                    + " 2>" + ApplicationConstants.LOG_DIR_EXPANSION_VAR + "/stderr"));

            // Set Container jar
            FileSystem hdfs = FileSystem.get(conf);
            org.apache.hadoop.fs.Path containerJarPath = new org.apache.hadoop.fs.Path(CONTAINER_JAR_PATH);
            LocalResource jar = HadoopUtils.setUpLocalResource(containerJarPath,hdfs);
            cCLC.setLocalResources(Collections.singletonMap(CONTAINER_JAR_PATH, jar));

            // Set Container CLASSPATH
            List<String> additionalClasspath = Collections.singletonList("./log4j.properties");
            Map<String, String> env = new HashMap<>();
            HadoopUtils.setUpEnv(env, conf, additionalClasspath);
            String paramFromClient = System.getenv(PARAM_FROM_CLIENT_TO_CONTAINER_NAME);
            env.put(PARAM_FROM_CLIENT_TO_CONTAINER_NAME, paramFromClient);
            cCLC.setEnvironment(env);

            return cCLC;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public void onContainersCompleted(List<ContainerStatus> statusOfContainers) {
        for (ContainerStatus status : statusOfContainers) {
            System.err.println("AppMaster: Container finished " + status.getContainerId());
            synchronized (this) {
                containerCount--;
            }
        }
    }

    @Override
    public void onError(Throwable e) {
    }

    @Override
    public void onNodesUpdated(List<NodeReport> nodeReports) {
    }

    @Override
    public void onShutdownRequest() {
    }

    @Override
    public float getProgress() {
        return 0;
    }
}
