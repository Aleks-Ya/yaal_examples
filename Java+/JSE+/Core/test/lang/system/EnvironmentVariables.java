package lang.system;

import org.junit.jupiter.api.Test;

import java.util.Map;

public class EnvironmentVariables {

    @Test
    public void printAllEnvironmentVariables() {
        System.out.println("ALL FROM System.getenv() \n");
        Map<String, String> envs = System.getenv();
        for (Map.Entry<String, String> entry : envs.entrySet()) {
            System.out.printf("%s=%s%n", entry.getKey(), entry.getValue());
        }
    }
}