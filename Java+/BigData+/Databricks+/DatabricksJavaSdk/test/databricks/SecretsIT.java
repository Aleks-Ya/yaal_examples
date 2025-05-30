package databricks;

import com.databricks.sdk.WorkspaceClient;
import com.databricks.sdk.core.error.platform.ResourceDoesNotExist;
import com.databricks.sdk.mixin.SecretsExt;
import com.databricks.sdk.service.workspace.PutSecret;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SecretsIT {
    private final WorkspaceClient w = new WorkspaceClient();
    private final SecretsExt secrets = w.secrets();
    private final SecretsHelper helper = new SecretsHelper(secrets);
    private final String scope = "temp_scope_1";

    @Test
    void createDeleteScope() {
        assertThat(helper.isScopeExist(scope)).isFalse();
        secrets.createScope(scope);
        assertThat(helper.isScopeExist(scope)).isTrue();
        secrets.deleteScope(scope);
        assertThat(helper.isScopeExist(scope)).isFalse();
    }

    @Test
    void createGetDeleteSecret() {
        secrets.createScope(scope);
        var key = "test_secret";
        var expValue = "pass1";
        var request = new PutSecret().setScope(scope).setKey(key).setStringValue(expValue);
        secrets.putSecret(request);
        var actValue = secrets.get(scope, key);
        secrets.deleteSecret(scope, key);
        secrets.deleteScope(scope);
        assertThat(actValue).isEqualTo(expValue);
    }

    @Test
    void getAbsentSecret() {
        secrets.createScope(scope);
        assertThatThrownBy(() -> secrets.get(scope, "absent_key")).isInstanceOf(ResourceDoesNotExist.class);
        secrets.deleteScope(scope);
    }

}