package yarn.minicluster;

import org.apache.hadoop.yarn.api.protocolrecords.GetApplicationsRequest;
import org.apache.hadoop.yarn.api.records.ApplicationId;
import org.apache.hadoop.yarn.api.records.ApplicationReport;
import org.apache.hadoop.yarn.api.records.ApplicationSubmissionContext;
import org.apache.hadoop.yarn.api.records.ContainerLaunchContext;
import org.apache.hadoop.yarn.api.records.Priority;
import org.apache.hadoop.yarn.api.records.Resource;
import org.apache.hadoop.yarn.client.api.YarnClient;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import org.apache.hadoop.yarn.exceptions.YarnException;
import org.apache.hadoop.yarn.server.MiniYARNCluster;
import org.apache.hadoop.yarn.util.Records;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Set;

import static org.apache.hadoop.yarn.api.records.YarnApplicationState.ACCEPTED;
import static org.assertj.core.api.Assertions.assertThat;

class ListAppTest {
    @Test
    void listApplicationAll() throws IOException, YarnException {
        try (var yarnCluster = new MiniYARNCluster("cluster1", 1, 1, 1, 1)) {
            var yarnConf = new YarnConfiguration();
            yarnCluster.init(yarnConf);
            yarnCluster.start();
            try (var yarnClient = YarnClient.createYarnClient()) {
                yarnClient.init(yarnConf);
                yarnClient.start();

                var appName1 = "test1";
                var appId1 = submitApp(yarnClient, appName1, Set.of());

                var appName2 = "test2";
                var appId2 = submitApp(yarnClient, appName2, Set.of());

                var apps = yarnClient.getApplications();
                var appIdList = apps.stream().map(ApplicationReport::getApplicationId).toList();
                assertThat(appIdList).containsExactlyInAnyOrder(appId1, appId2);
            }
        }
    }

    @Test
    void listApplicationByTag() throws IOException, YarnException {
        try (var yarnCluster = new MiniYARNCluster("cluster1", 1, 1, 1, 1)) {
            var yarnConf = new YarnConfiguration();
            yarnCluster.init(yarnConf);
            yarnCluster.start();
            try (var yarnClient = YarnClient.createYarnClient()) {
                yarnClient.init(yarnConf);
                yarnClient.start();

                var appName1 = "test1";
                var tag1 = "tag1";
                var appId1 = submitApp(yarnClient, appName1, Set.of(tag1));

                var appName2 = "test2";
                var tag2 = "tag2";
                var appId2 = submitApp(yarnClient, appName2, Set.of(tag1, tag2));

                var appName3 = "test3";
                var appId3 = submitApp(yarnClient, appName3, Set.of());

                var apps = yarnClient.getApplications();
                var appIdList = apps.stream().map(ApplicationReport::getApplicationId).toList();
                assertThat(appIdList).containsExactlyInAnyOrder(appId1, appId2, appId3);

                var request = Records.newRecord(GetApplicationsRequest.class);
                request.setApplicationTags(Set.of(tag1));
                var appsTag1 = yarnClient.getApplications(request);
                var appIdListTag1 = appsTag1.stream().map(ApplicationReport::getApplicationId).toList();
                assertThat(appIdListTag1).containsExactlyInAnyOrder(appId1, appId2);
            }
        }
    }

    @Test
    void listApplicationByState() throws IOException, YarnException {
        try (var yarnCluster = new MiniYARNCluster("cluster1", 1, 1, 1, 1)) {
            var yarnConf = new YarnConfiguration();
            yarnCluster.init(yarnConf);
            yarnCluster.start();
            try (var yarnClient = YarnClient.createYarnClient()) {
                yarnClient.init(yarnConf);
                yarnClient.start();

                var appName1 = "test1";
                var tag1 = "tag1";
                var appId1 = submitApp(yarnClient, appName1, Set.of(tag1));

                var appName2 = "test2";
                var tag2 = "tag2";
                var appId2 = submitApp(yarnClient, appName2, Set.of(tag1, tag2));

                var appName3 = "test3";
                var appId3 = submitApp(yarnClient, appName3, Set.of());

                var apps = yarnClient.getApplications();
                var appIdList = apps.stream().map(ApplicationReport::getApplicationId).toList();
                assertThat(appIdList).containsExactlyInAnyOrder(appId1, appId2, appId3);

                var request = Records.newRecord(GetApplicationsRequest.class);
                request.setApplicationTags(Set.of(tag1));
                request.setApplicationStates(Set.of(ACCEPTED.name()));
                var appsTag1 = yarnClient.getApplications(request);
                var appIdListTag1 = appsTag1.stream().map(ApplicationReport::getApplicationId).toList();
                assertThat(appIdListTag1).containsExactlyInAnyOrder(appId1, appId2);
            }
        }
    }

    private ApplicationId submitApp(YarnClient yarnClient, String appName, Set<String> tags) throws YarnException, IOException {
        var newApp = yarnClient.createApplication();
        var appId = newApp.getNewApplicationResponse().getApplicationId();
        var appContext = Records.newRecord(ApplicationSubmissionContext.class);
        appContext.setApplicationId(appId);
        appContext.setApplicationName(appName);
        appContext.setQueue("default");
        appContext.setApplicationTags(tags);

        var pri = Records.newRecord(Priority.class);
        pri.setPriority(1);
        appContext.setPriority(pri);

        var amContainer = Records.newRecord(ContainerLaunchContext.class);
        appContext.setAMContainerSpec(amContainer);
        appContext.setResource(Resource.newInstance(256, 1));

        yarnClient.submitApplication(appContext);
        return appId;
    }
}
