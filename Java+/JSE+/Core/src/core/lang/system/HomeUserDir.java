package core.lang.system;

import static java.lang.System.out;

/**
 * Получение домашней папки текущего пользователя.
 */
public class HomeUserDir {
    public static void main(String[] args) {
        out.println();
        out.printf("System.getenv(\"HOME\")           -> %s%n", System.getenv("HOME"));
        out.printf("System.getProperty(\"user.home\") -> %s%n", System.getProperty("user.home"));
    }
}