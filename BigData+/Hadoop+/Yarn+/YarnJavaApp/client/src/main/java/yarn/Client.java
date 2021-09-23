package yarn;

import de.danielbechler.diff.ObjectDifferBuilder;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.yarn.api.ApplicationConstants;
import org.apache.hadoop.yarn.api.records.ApplicationId;
import org.apache.hadoop.yarn.api.records.ApplicationReport;
import org.apache.hadoop.yarn.api.records.ApplicationSubmissionContext;
import org.apache.hadoop.yarn.api.records.ContainerLaunchContext;
import org.apache.hadoop.yarn.api.records.LocalResource;
import org.apache.hadoop.yarn.api.records.Resource;
import org.apache.hadoop.yarn.api.records.YarnApplicationState;
import org.apache.hadoop.yarn.client.api.YarnClient;
import org.apache.hadoop.yarn.client.api.YarnClientApplication;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import org.apache.hadoop.yarn.util.Records;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static yarn.Utils.APPLICATION_MASTER_JAR_PATH;
import static yarn.Utils.CONTAINER_JAR_PATH;

public class Client {
    private static final String PARAM_FROM_CLIENT_TO_CONTAINER_NAME = "param_from_client";

    public static void main(String[] args) throws IOException {
        //Use class from dependency
        System.out.println(ObjectDifferBuilder.buildDefault().compare("abc", "axc"));

        System.out.println("Client: Initializing");
        System.out.println("Environment vars: " + System.getenv());
        System.out.println("System properties: " + System.getProperties());

        String pwdDir = System.getenv("PWD");
        System.out.println("pwdDir: " + pwdDir);
        System.out.println("Files in pwdDir: " + Files.list(Paths.get(pwdDir))
                .map(java.nio.file.Path::toAbsolutePath)
                .collect(Collectors.toList()));

        try {
            new Client().run();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void run() throws Exception {
        ContainerLaunchContext amCLC = Records.newRecord(ContainerLaunchContext.class);
        amCLC.setCommands(Collections.singletonList("$JAVA_HOME/bin/java"
                + " -Xmx256M"
                + " -XX:+PrintFlagsFinal"
                + " yarn.AppMaster"
                + " 1>" + ApplicationConstants.LOG_DIR_EXPANSION_VAR + "/stdout"
                + " 2>" + ApplicationConstants.LOG_DIR_EXPANSION_VAR + "/stderr"));

        YarnConfiguration conf = new YarnConfiguration();

        // Set AM CLASSPATH
        List<String> additionalClasspath = Collections.singletonList("./log4j.properties");
        Map<String, String> env = new HashMap<>();
        Utils.setUpEnv(env, conf, additionalClasspath);
        env.put(PARAM_FROM_CLIENT_TO_CONTAINER_NAME, "123");
        amCLC.setEnvironment(env);

        // Set AM jar
        FileSystem hdfs = FileSystem.get(conf);
        Path appsDir = new Path("/apps");
        hdfs.delete(appsDir, true);
        hdfs.mkdirs(appsDir);
        URI localAmJarUri = new File("/tmp/am.jar").toURI();
        hdfs.copyFromLocalFile(new Path(localAmJarUri), APPLICATION_MASTER_JAR_PATH);
        URI localContainerJarUri = new File("/tmp/container.jar").toURI();
        hdfs.copyFromLocalFile(new Path(localContainerJarUri), CONTAINER_JAR_PATH);

        Map<String, LocalResource> localResourceMap = new HashMap<>();
        LocalResource yarnApplicationJar = Utils.setUpLocalResource(APPLICATION_MASTER_JAR_PATH, hdfs);
        localResourceMap.put(Utils.APPLICATION_MASTER_JAR_NAME, yarnApplicationJar);
        amCLC.setLocalResources(localResourceMap);

        // Set AM resources
        Resource res = Records.newRecord(Resource.class);
        res.setMemorySize(256);
        res.setVirtualCores(1);

        // Create Yarn Client
        YarnClient client = YarnClient.createYarnClient();
        client.init(conf);
        client.start();

        // Create Application
        YarnClientApplication app = client.createApplication();

        // Create ApplicationSubmissionContext
        ApplicationSubmissionContext appContext = app.getApplicationSubmissionContext();
        appContext.setApplicationName("YarnApplication");
        appContext.setQueue("default");
        appContext.setAMContainerSpec(amCLC);
        appContext.setResource(res);

        // Submit Application
        ApplicationId id = appContext.getApplicationId();
        System.out.println("Client: Submitting " + id);
        client.submitApplication(appContext);

        ApplicationReport appReport = client.getApplicationReport(id);
        YarnApplicationState appState = appReport.getYarnApplicationState();
        while (appState != YarnApplicationState.FINISHED
                && appState != YarnApplicationState.KILLED
                && appState != YarnApplicationState.FAILED) {
            Thread.sleep(1000);
            appReport = client.getApplicationReport(id);
            appState = appReport.getYarnApplicationState();
        }

        System.out.println("Client: Finished " + id + " with state " + appState);
    }

}
