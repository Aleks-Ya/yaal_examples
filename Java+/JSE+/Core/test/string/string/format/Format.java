package string.string.format;

import org.junit.Test;

import static java.lang.String.format;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Примеры работы с методом String#format.
 */
public class Format {
    @Test
    public void string() {
        assertThat(format("\nСтрока: %s%n", "Hello!"), equalTo("\nСтрока: Hello!\n"));

        assertThat(format("Перенос строки 1: %n"), equalTo("Перенос строки 1: \n"));
        assertThat(format("Перенос строки 2: \n"), equalTo("Перенос строки 2: \n"));

        // Знакоместа для строк:
        assertThat(format("%30s%n", "The first string."), equalTo("             The first string.\n"));
        assertThat(format("%30s%n", "The 2nd string!"), equalTo("               The 2nd string!\n"));

    }

    @Test
    public void argOrder() {
        assertThat(format("Порядок аргументов: %2$s %1$s %n", "2nd", "1st"), equalTo("Порядок аргументов: 1st 2nd \n"));
    }

    @Test
    public void nulls() {
        //noinspection MalformedFormatString
        assertThat(format("Null: %s,%d,%f%n", null, null, null), equalTo("Null: null,null,null\n"));
    }

    @Test
    public void integer() {
        assertThat(format("\nЦелое число: %d%n", 300), equalTo("\nЦелое число: 300\n"));
        assertThat(format("Ведущие нули: %05d %n", 300), equalTo("Ведущие нули: 00300 \n"));
        assertThat(format("Знакоместа в начале числа: %20d %n", 300), equalTo("Знакоместа в начале числа:                  300 \n"));
    }

    @Test
    public void floating() {
        assertThat(format("Знакоместа в начале числа: %15f %n", 9.8), equalTo("Знакоместа в начале числа:        9.800000 \n"));
        assertThat(format("Ведущие нули: %010f %n", 9.8), equalTo("Ведущие нули: 009.800000 \n"));
        assertThat(format("Ведущий ноль и десятичные разряды : %01.2f %n", .14), equalTo("Ведущий ноль и десятичные разряды : 0.14 \n"));
        assertThat(format("Вещественное число: %f%n", 3.14), equalTo("Вещественное число: 3.140000\n"));
        assertThat(format("Десятичные разряды: %.2f%n", 3.14), equalTo("Десятичные разряды: 3.14\n"));
        assertThat(format("Разделители групп разрядов: %,d%n", 1000000), equalTo("Разделители групп разрядов: 1,000,000\n"));

    }
}
