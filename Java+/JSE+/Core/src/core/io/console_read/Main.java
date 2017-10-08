package core.io.console_read;

import java.io.Console;

/**
 * Прочитать из консоли строку.
 */
public class Main {
    public static void main(String[] args) {
        Console console = System.console();
        if (console != null) {
            System.out.println("Enter empty line to exit");
            while (true) {
                String line = console.readLine();
                if (!line.isEmpty()) {
                    console.printf("'%s'\n", line);
                } else {
                    System.exit(0);
                }
            }
        } else {
            //Чтобы была консоль, нужно запускать из командной строки
            System.out.println("System.console() is null");
        }
    }
}
