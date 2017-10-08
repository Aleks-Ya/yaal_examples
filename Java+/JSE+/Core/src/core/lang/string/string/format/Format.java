package core.lang.string.string.format;

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
        Assert.assertThat(format("\nСтрока: %s%n", "Hello!"), CoreMatchers.equalTo("\nСтрока: Hello!\n"));

        Assert.assertThat(format("Перенос строки 1: %n"), CoreMatchers.equalTo("Перенос строки 1: \n"));
        Assert.assertThat(format("Перенос строки 2: \n"), CoreMatchers.equalTo("Перенос строки 2: \n"));

        // Знакоместа для строк:
        Assert.assertThat(format("%30s%n", "The first string."), CoreMatchers.equalTo("             The first string.\n"));
        Assert.assertThat(format("%30s%n", "The 2nd string!"), CoreMatchers.equalTo("               The 2nd string!\n"));

    }

    @Test
    public void argOrder() {
        Assert.assertThat(format("Порядок аргументов: %2$s %1$s %n", "2nd", "1st"), CoreMatchers.equalTo("Порядок аргументов: 1st 2nd \n"));
    }

    @Test
    public void nulls() {
        //noinspection MalformedFormatString
        Assert.assertThat(format("Null: %s,%d,%f%n", null, null, null), CoreMatchers.equalTo("Null: null,null,null\n"));
    }

    @Test
    public void integer() {
        Assert.assertThat(format("\nЦелое число: %d%n", 300), CoreMatchers.equalTo("\nЦелое число: 300\n"));
        Assert.assertThat(format("Ведущие нули: %05d %n", 300), CoreMatchers.equalTo("Ведущие нули: 00300 \n"));
        Assert.assertThat(format("Знакоместа в начале числа: %20d %n", 300), CoreMatchers.equalTo("Знакоместа в начале числа:                  300 \n"));
    }

    @Test
    public void floating() {
        Assert.assertThat(format("Знакоместа в начале числа: %15f %n", 9.8), CoreMatchers.equalTo("Знакоместа в начале числа:        9.800000 \n"));
        Assert.assertThat(format("Ведущие нули: %010f %n", 9.8), CoreMatchers.equalTo("Ведущие нули: 009.800000 \n"));
        Assert.assertThat(format("Ведущий ноль и десятичные разряды : %01.2f %n", .14), CoreMatchers.equalTo("Ведущий ноль и десятичные разряды : 0.14 \n"));
        Assert.assertThat(format("Вещественное число: %f%n", 3.14), CoreMatchers.equalTo("Вещественное число: 3.140000\n"));
        Assert.assertThat(format("Десятичные разряды: %.2f%n", 3.14), CoreMatchers.equalTo("Десятичные разряды: 3.14\n"));
        Assert.assertThat(format("Разделители групп разрядов: %,d%n", 1000000), CoreMatchers.equalTo("Разделители групп разрядов: 1,000,000\n"));

    }
}
