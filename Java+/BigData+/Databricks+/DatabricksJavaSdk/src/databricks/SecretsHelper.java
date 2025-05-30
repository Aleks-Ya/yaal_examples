package databricks;

import com.databricks.sdk.mixin.SecretsExt;

public class SecretsHelper {
    private final SecretsExt secrets;

    public SecretsHelper(SecretsExt secrets) {
        this.secrets = secrets;
    }

    public Boolean isScopeExist(String scope) {
        for (var secretScope : secrets.listScopes()) {
            if (scope.equals(secretScope.getName())) {
                return true;
            }
        }
        return false;
    }
}
