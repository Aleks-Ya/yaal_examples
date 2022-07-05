package yarn3.local;

import org.apache.hadoop.yarn.api.records.ApplicationReport;
import org.apache.hadoop.yarn.client.api.YarnClient;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import org.apache.hadoop.yarn.exceptions.YarnException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * Connects to "BigData+/Hadoop+/HadoopDocker+/Hadoop3+/HadoopCluster".
 */
class LocalYarnTest {
    @Test
    void listApps() throws IOException, YarnException {
        var conf = new YarnConfiguration();
        conf.set("yarn.resourcemanager.address", "master-service:8032");
        try (var yarnClient = YarnClient.createYarnClient()) {
            yarnClient.init(conf);
            yarnClient.start();
            var apps = yarnClient.getApplications();
            var appIdList = apps.stream().map(ApplicationReport::getApplicationId).toList();
            System.out.println(appIdList);
        }
    }
}
