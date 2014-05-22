import java.io.PrintStream;

public class Main {
    public static void main(String[] args) {
        PrintStream out = System.out;

        out.printf("Null: %s,%d,%f%n", null, null, null);
        out.printf("Порядок аргументов: %2$s %1$s %n", "2nd", "1st");


        out.printf("\nСтрока: %s%n", "Hello!");
        out.printf("Перенос строки 1: %n");
        out.printf("Перенос строки 2: \n");

        out.println("\nЗнакоместа для строк:");
        out.printf("%50s%n", "The first string.");
        out.printf("%50s%n", "The 2nd string!");

        out.printf("\nЦелое число: %d%n", 300);
        out.printf("Знакоместа в начале числа: %20d %15f %n", 300, 9.8);
        out.printf("Вещественное число: %f%n", 3.14);
        out.printf("Десятичные разряды: %.2f%n", 3.14);
    }
}
