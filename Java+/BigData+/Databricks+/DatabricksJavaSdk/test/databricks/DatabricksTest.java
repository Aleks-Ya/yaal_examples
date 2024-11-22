package databricks;

import com.databricks.sdk.WorkspaceClient;
import com.databricks.sdk.service.compute.ListClustersRequest;
import org.junit.jupiter.api.Test;

class DatabricksTest {
    @Test
    void listClusters() {
        var w = new WorkspaceClient();
        for (var c : w.clusters().list(new ListClustersRequest())) {
            System.out.println(c.getClusterName());
        }
    }
}