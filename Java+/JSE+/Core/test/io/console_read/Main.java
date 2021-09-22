package io.console_read;

/**
 * Прочитать из консоли строку.
 */
public class Main {
    public static void main(String[] args) {
        var console = System.console();
        if (console != null) {
            System.out.println("Enter empty line to exit");
            while (true) {
                var line = console.readLine();
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
