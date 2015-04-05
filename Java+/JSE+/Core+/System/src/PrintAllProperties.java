import java.util.Map;
import java.util.Properties;

/**
 * Выводит в консоль все системные переменные.
 */
public class PrintAllProperties {

    public static void main(String[] args) {
        System.out.println("ALL FROM System.getProperties() \n");
        Properties envs = System.getProperties();
        for (Map.Entry<Object, Object> entry : envs.entrySet()) {
            System.out.printf("%s=%s%n", entry.getKey(), entry.getValue());
        }
    }
}