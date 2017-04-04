package file.jar_working_dir;

import java.util.Map;

import static java.lang.System.out;

/**
 * Определение папки, в которой расположен исполняемый в данный момент файл.
 */
public class Main {

    public static void main(String[] args) {
        out.printf("System.getProperty(\"sun.java.command\") = %s%n", System.getProperty("sun.java.command"));
        out.printf("System.getProperty(\"java.class.path\")  = %s%n", System.getProperty("java.class.path"));
        //printAll();
    }

    /**
     * Печатает значения всех переменных окружения и системных свойств.
     */
    private static void printAll() {
        out.println("ENVIRONMENT");
        for (Map.Entry<String, String> env : System.getenv().entrySet()) {
            out.printf("%s=%s%n", env.getKey(), env.getValue());
        }
        out.println();

        out.println("SYSTEM VARIABLES");
        for (Map.Entry<Object, Object> prop : System.getProperties().entrySet()) {
            out.printf("%s=%s%n", prop.getKey(), prop.getValue());
        }
        out.println();
    }
}