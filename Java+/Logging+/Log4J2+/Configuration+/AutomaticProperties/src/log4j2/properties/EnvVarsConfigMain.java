package log4j2.properties;

import org.apache.logging.log4j.LogManager;
import uk.org.webcompere.systemstubs.environment.EnvironmentVariables;

/**
 * Overwrite property "appender.console.layout.pattern" defined in "log4j2.properties"
 * with environment variable.
 */
public class EnvVarsConfigMain {
    private static final String ENV_VAR_NAME = "CONSOLE_LAYOUT_PATTERN";

    public static void main(String[] args) throws Exception {
        patternInEnvVar();
        patternInEnvVarDefaultValue();
    }

    private static void patternInEnvVar() throws Exception {
        LogManager.shutdown();
        var envVars = new EnvironmentVariables(ENV_VAR_NAME, "FROM ENV VAR: %msg%n");
        envVars.setup();
        assert !System.getenv(ENV_VAR_NAME).isBlank();
        var log = LogManager.getLogger(EnvVarsConfigMain.class);
        log.info("Expect pattern from env var");
        envVars.teardown();
    }

    private static void patternInEnvVarDefaultValue() {
        LogManager.shutdown();
        assert System.getenv(ENV_VAR_NAME).isBlank();
        var log = LogManager.getLogger(EnvVarsConfigMain.class);
        log.info("Expect default pattern");
    }
}