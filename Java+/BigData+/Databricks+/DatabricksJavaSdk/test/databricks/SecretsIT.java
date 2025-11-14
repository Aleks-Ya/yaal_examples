package databricks;

import com.databricks.sdk.WorkspaceClient;
import com.databricks.sdk.core.error.platform.ResourceDoesNotExist;
import com.databricks.sdk.mixin.SecretsExt;
import com.databricks.sdk.service.workspace.CreateScope;
import com.databricks.sdk.service.workspace.DeleteSecret;
import com.databricks.sdk.service.workspace.PutSecret;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SecretsIT {
    private final WorkspaceClient w = new WorkspaceClient();
    private final SecretsExt secrets = w.secrets();
    private final SecretsHelper helper = new SecretsHelper(secrets);
    private final String scope = "temp_scope_1";

    @Test
    void listScopes() {
        var scopesIterable = secrets.listScopes();
        var scopeList = Lists.newArrayList(scopesIterable);
        scopeList.forEach(System.out::println);
        assertThat(scopeList).isNotEmpty();
    }

    @Test
    void createDeleteScope() {
        assertThat(helper.isScopeExist(scope)).isFalse();
        var createScope = new CreateScope().setScope(scope);
        secrets.createScope(createScope);
        assertThat(helper.isScopeExist(scope)).isTrue();
        secrets.deleteScope(scope);
        assertThat(helper.isScopeExist(scope)).isFalse();
    }

    @Test
    void createGetDeleteSecret() {
        var createScope = new CreateScope().setScope(scope);
        secrets.createScope(createScope);
        var key = "test_secret";
        var expValue = "pass1";
        var request = new PutSecret().setScope(scope).setKey(key).setStringValue(expValue);
        secrets.putSecret(request);
        var actValue = secrets.get(scope, key);
        var deleteSecret = new DeleteSecret().setScope(scope).setKey(key);
        secrets.deleteSecret(deleteSecret);
        secrets.deleteScope(scope);
        assertThat(actValue).isEqualTo(expValue);
    }

    @Test
    void getAbsentSecret() {
        var createScope = new CreateScope().setScope(scope);
        secrets.createScope(createScope);
        assertThatThrownBy(() -> secrets.get(scope, "absent_key")).isInstanceOf(ResourceDoesNotExist.class);
        secrets.deleteScope(scope);
    }

}