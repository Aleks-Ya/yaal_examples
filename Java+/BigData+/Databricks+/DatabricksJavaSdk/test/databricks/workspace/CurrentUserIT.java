package databricks.workspace;

import com.databricks.sdk.WorkspaceClient;
import com.databricks.sdk.service.iam.CurrentUserAPI;
import org.junit.jupiter.api.Test;

class CurrentUserIT {
    private final WorkspaceClient w = new WorkspaceClient();
    private final CurrentUserAPI currentUser = w.currentUser();

    @Test
    void me() {
        System.out.println(currentUser.me());
    }
}