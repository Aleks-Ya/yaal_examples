package stubs;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import uk.org.webcompere.systemstubs.environment.EnvironmentVariables;
import uk.org.webcompere.systemstubs.jupiter.SystemStub;
import uk.org.webcompere.systemstubs.jupiter.SystemStubsExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SystemStubsExtension.class)
class AddEnvVarExtensionTest {
    private static final String ENV_VAR_NAME = "my_var";
    private static final String ENV_VAR_VALUE = "my_value";

    @SystemStub
    @SuppressWarnings("unused")
    private final EnvironmentVariables variables = new EnvironmentVariables(ENV_VAR_NAME, ENV_VAR_VALUE);

    @Test
    void useEnvVar() {
        assertThat(System.getenv(ENV_VAR_NAME)).isEqualTo(ENV_VAR_VALUE);
    }
}
