package databricks.workspace;

import com.databricks.sdk.WorkspaceClient;
import com.databricks.sdk.mixin.ClustersExt;
import com.databricks.sdk.service.compute.ListClustersRequest;
import org.junit.jupiter.api.Test;

class ClustersIT {
    private final WorkspaceClient w = new WorkspaceClient();
    private final ClustersExt clusters = w.clusters();

    @Test
    void listClusters() {
        var request = new ListClustersRequest();
        for (var cluster : clusters.list(request)) {
            System.out.println(cluster.getClusterName());
        }
    }
}