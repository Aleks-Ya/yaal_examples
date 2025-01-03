package yarn3.minicluster;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.yarn.api.records.ApplicationReport;
import org.apache.hadoop.yarn.api.records.ApplicationSubmissionContext;
import org.apache.hadoop.yarn.api.records.ContainerLaunchContext;
import org.apache.hadoop.yarn.api.records.FinalApplicationStatus;
import org.apache.hadoop.yarn.api.records.LocalResource;
import org.apache.hadoop.yarn.api.records.LocalResourceType;
import org.apache.hadoop.yarn.api.records.LocalResourceVisibility;
import org.apache.hadoop.yarn.api.records.Priority;
import org.apache.hadoop.yarn.api.records.Resource;
import org.apache.hadoop.yarn.client.api.YarnClient;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import org.apache.hadoop.yarn.exceptions.YarnException;
import org.apache.hadoop.yarn.server.MiniYARNCluster;
import org.apache.hadoop.yarn.util.ConverterUtils;
import org.apache.hadoop.yarn.util.Records;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.apache.hadoop.yarn.api.records.YarnApplicationState.FAILED;
import static org.apache.hadoop.yarn.api.records.YarnApplicationState.FINISHED;
import static org.apache.hadoop.yarn.api.records.YarnApplicationState.KILLED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

class SubmitApplicationTest {
    @Test
    void submitApplication() throws IOException, YarnException {
        try (var yarnCluster = new MiniYARNCluster("cluster1", 1, 1, 1, 1)) {
            var yarnConf = new YarnConfiguration();
            yarnCluster.init(yarnConf);
            yarnCluster.start();
            try (var yarnClient = YarnClient.createYarnClient()) {
                yarnClient.init(yarnConf);
                yarnClient.start();

                var newApp = yarnClient.createApplication();
                var appId = newApp.getNewApplicationResponse().getApplicationId();
                var appContext = Records.newRecord(ApplicationSubmissionContext.class);
                appContext.setApplicationId(appId);
                appContext.setApplicationName("test");
                appContext.setQueue("default");

                var pri = Records.newRecord(Priority.class);
                pri.setPriority(1);
                appContext.setPriority(pri);

                var amContainer = Records.newRecord(ContainerLaunchContext.class);
                appContext.setAMContainerSpec(amContainer);
                appContext.setResource(Resource.newInstance(256, 1));

                var appId2 = yarnClient.submitApplication(appContext);

                System.out.println("ID: " + appId2);

                var apps = yarnClient.getApplications();
                var appIdList = apps.stream().map(ApplicationReport::getApplicationId).toList();
                assertThat(appId).isEqualTo(appId2);
                assertThat(appIdList).contains(appId).contains(appId2);
            }
        }
    }

    @Test
    void submitApplicationAndWaitFinished() throws IOException, YarnException {
        try (var yarnCluster = new MiniYARNCluster("cluster1", 1, 1, 1, 1)) {
            var yarnConf = new YarnConfiguration();
            yarnCluster.init(yarnConf);
            yarnCluster.start();
            try (var yarnClient = YarnClient.createYarnClient()) {
                yarnClient.init(yarnConf);
                yarnClient.start();

                var newApp = yarnClient.createApplication();
                var appId = newApp.getNewApplicationResponse().getApplicationId();
                var appContext = Records.newRecord(ApplicationSubmissionContext.class);
                appContext.setApplicationId(appId);
                appContext.setApplicationName("test");
                appContext.setQueue("default");

                var pri = Records.newRecord(Priority.class);
                pri.setPriority(1);
                appContext.setPriority(pri);

                var amContainer = Records.newRecord(ContainerLaunchContext.class);
                appContext.setAMContainerSpec(amContainer);
                appContext.setResource(Resource.newInstance(256, 1));

                yarnClient.submitApplication(appContext);

                await().pollInterval(3, SECONDS).until(() -> {
                    var report = yarnClient.getApplicationReport(appId);
                    assertThat(report).isNotNull();
                    var state = report.getYarnApplicationState();
                    assertThat(state).isNotNull();
                    return state == FINISHED || state == KILLED || state == FAILED;
                });
                var report = yarnClient.getApplicationReport(appId);
                assertThat(report.getYarnApplicationState()).isEqualTo(FAILED);
                assertThat(report.getFinalApplicationStatus()).isEqualTo(FinalApplicationStatus.FAILED);
            }
        }
    }

    //Not finished
    @Test
    void submitApplicationWithJar() throws IOException, YarnException {
        try (var yarnCluster = new MiniYARNCluster("cluster1", 1, 1, 1, 1)) {
            var yarnConf = new YarnConfiguration();
            yarnCluster.init(yarnConf);
            yarnCluster.start();
            try (var yarnClient = YarnClient.createYarnClient()) {
                yarnClient.init(yarnConf);
                yarnClient.start();

                var newApp = yarnClient.createApplication();
                var appId = newApp.getNewApplicationResponse().getApplicationId();
                var appContext = Records.newRecord(ApplicationSubmissionContext.class);
                appContext.setApplicationId(appId);
                appContext.setApplicationName("test");
                appContext.setQueue("default");

                var pri = Records.newRecord(Priority.class);
                pri.setPriority(1);
                appContext.setPriority(pri);

                var amContainer = Records.newRecord(ContainerLaunchContext.class);
                appContext.setAMContainerSpec(amContainer);
                appContext.setResource(Resource.newInstance(256, 1));

                var jarPath = new Path("/home/aleks/pr/home/yaal_examples/BigData+/Hadoop+/Yarn+/YarnJavaApp/am/target/yarn-java-app-am-1.jar");
                var appMasterJar = Records.newRecord(LocalResource.class);
                var jarStat = FileSystem.get(yarnConf).getFileStatus(jarPath);
                appMasterJar.setResource(ConverterUtils.getYarnUrlFromPath(jarPath));
                appMasterJar.setSize(jarStat.getLen());
                appMasterJar.setTimestamp(jarStat.getModificationTime());
                appMasterJar.setType(LocalResourceType.FILE);
                appMasterJar.setVisibility(LocalResourceVisibility.PUBLIC);
                amContainer.setLocalResources(Map.of("simple-yarn-app-1.1.0.jar", appMasterJar));

                yarnClient.submitApplication(appContext);

                await().pollInterval(3, SECONDS).until(() -> {
                    var report = yarnClient.getApplicationReport(appId);
                    assertThat(report).isNotNull();
                    var state = report.getYarnApplicationState();
                    assertThat(state).isNotNull();
                    return state == FINISHED || state == KILLED || state == FAILED;
                });
                var report = yarnClient.getApplicationReport(appId);
                assertThat(report.getYarnApplicationState()).isEqualTo(FAILED);
                assertThat(report.getFinalApplicationStatus()).isEqualTo(FinalApplicationStatus.FAILED);
            }
        }
    }

}
