package stubs;

import org.junit.jupiter.api.Test;
import uk.org.webcompere.systemstubs.environment.EnvironmentVariables;

import static org.assertj.core.api.Assertions.assertThat;

class AddEnvVarLocallyTest {
    @Test
    void setEnvVarInConstructor() throws Exception {
        var envVarName = "my_var";
        var envVarValue = "my_value";
        var environmentVariables = new EnvironmentVariables(envVarName, envVarValue);
        assertThat(System.getenv(envVarName)).isNull();
        environmentVariables.setup();
        assertThat(System.getenv(envVarName)).isEqualTo(envVarValue);
        environmentVariables.teardown();
        assertThat(System.getenv(envVarName)).isNull();
    }

    @Test
    void setEnvVarInMethod() throws Exception {
        var environmentVariables = new EnvironmentVariables();
        environmentVariables.setup();
        var envVarName = "my_var";
        var envVarValue = "my_value";
        assertThat(System.getenv(envVarName)).isNull();
        environmentVariables.set(envVarName, envVarValue);
        assertThat(System.getenv(envVarName)).isEqualTo(envVarValue);
        environmentVariables.teardown();
        assertThat(System.getenv(envVarName)).isNull();
    }
}
