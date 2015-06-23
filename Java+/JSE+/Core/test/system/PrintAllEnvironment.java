package system;

import java.util.Map;

/**
 * Выводит в консоль все переменные окружения.
 */
public class PrintAllEnvironment {

    public static void main(String[] args) {
        System.out.println("ALL FROM System.getenv() \n");
        Map<String, String> envs = System.getenv();
        for (Map.Entry<String, String> entry : envs.entrySet()) {
            System.out.printf("%s=%s%n", entry.getKey(), entry.getValue());
        }
    }
}