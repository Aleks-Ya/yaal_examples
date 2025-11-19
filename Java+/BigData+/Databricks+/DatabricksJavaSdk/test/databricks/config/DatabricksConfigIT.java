package databricks.config;

import com.databricks.sdk.WorkspaceClient;
import com.databricks.sdk.core.DatabricksConfig;
import org.junit.jupiter.api.Test;

class DatabricksConfigIT {
    @Test
    void manuallySetAccessToken() {
        var config = new DatabricksConfig()
                .setHost("https://XXX.cloud.databricks.com")
                .setToken("XXX");
        var w = new WorkspaceClient(config);
        var currentUser = w.currentUser();
        System.out.println(currentUser.me());
    }
}