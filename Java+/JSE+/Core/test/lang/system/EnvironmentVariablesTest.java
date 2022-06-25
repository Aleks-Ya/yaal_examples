package lang.system;

import org.junit.jupiter.api.Test;

class EnvironmentVariablesTest {

    @Test
    void printAllEnvironmentVariables() {
        System.out.println("ALL FROM System.getenv() \n");
        var envs = System.getenv();
        for (var entry : envs.entrySet()) {
            System.out.printf("%s=%s%n", entry.getKey(), entry.getValue());
        }
    }
}