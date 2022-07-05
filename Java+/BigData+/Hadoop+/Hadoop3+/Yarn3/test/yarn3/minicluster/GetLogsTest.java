package yarn3.minicluster;

import org.apache.hadoop.yarn.api.records.ApplicationSubmissionContext;
import org.apache.hadoop.yarn.api.records.ContainerLaunchContext;
import org.apache.hadoop.yarn.api.records.FinalApplicationStatus;
import org.apache.hadoop.yarn.api.records.Priority;
import org.apache.hadoop.yarn.api.records.Resource;
import org.apache.hadoop.yarn.client.api.YarnClient;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import org.apache.hadoop.yarn.exceptions.YarnException;
import org.apache.hadoop.yarn.logaggregation.ContainerLogsRequest;
import org.apache.hadoop.yarn.logaggregation.LogCLIHelpers;
import org.apache.hadoop.yarn.server.MiniYARNCluster;
import org.apache.hadoop.yarn.util.Records;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.apache.hadoop.yarn.api.records.YarnApplicationState.FAILED;
import static org.apache.hadoop.yarn.api.records.YarnApplicationState.FINISHED;
import static org.apache.hadoop.yarn.api.records.YarnApplicationState.KILLED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

class GetLogsTest {

    @Test
    void submitApplicationAndWaitFinished() throws IOException, YarnException {
        try (var yarnCluster = new MiniYARNCluster("cluster1", 1, 1, 1, 1)) {
            var yarnConf = new YarnConfiguration();
            yarnConf.setBoolean("yarn.log-aggregation-enable", true);
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

                LogCLIHelpers logCliHelper = new LogCLIHelpers();
                logCliHelper.setConf(yarnConf);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                var logRequest = Records.newRecord(ContainerLogsRequest.class);
                var logRequest = new ContainerLogsRequest();
                logRequest.setAppId(appId);
                logCliHelper.dumpAllContainersLogs(logRequest);
                String content = baos.toString(StandardCharsets.UTF_8);
                System.out.println("Log: " + content);
            }
        }
    }
}
