package lang.string.string.format;

import org.junit.jupiter.api.Test;

import java.text.NumberFormat;
import java.util.Locale;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Примеры работы с методом String#format.
 */
class Format {
    @Test
    void string() {
        assertThat(format("\nСтрока: %s%n", "Hello!")).isEqualTo("\nСтрока: Hello!\n");

        assertThat(format("Перенос строки 1: %n")).isEqualTo("Перенос строки 1: \n");
        assertThat(format("Перенос строки 2: \n")).isEqualTo("Перенос строки 2: \n");

        // Знакоместа для строк:
        assertThat(format("%30s%n", "The first string.")).isEqualTo("             The first string.\n");
        assertThat(format("%30s%n", "The 2nd string!")).isEqualTo("               The 2nd string!\n");

    }

    @Test
    void argOrder() {
        assertThat(format("Порядок аргументов: %2$s %1$s %n", "2nd", "1st")).isEqualTo("Порядок аргументов: 1st 2nd \n");
    }

    @Test
    void nulls() {
        //noinspection MalformedFormatString
        assertThat(format("Null: %s,%d,%f%n", null, null, null)).isEqualTo("Null: null,null,null\n");
    }

    @Test
    void integer() {
        assertThat(format("\nЦелое число: %d%n", 300)).isEqualTo("\nЦелое число: 300\n");
        assertThat(format("Ведущие нули: %05d %n", 300)).isEqualTo("Ведущие нули: 00300 \n");
        assertThat(format("Знакоместа в начале числа: %20d %n", 300)).isEqualTo("Знакоместа в начале числа:                  300 \n");
    }

    @Test
    void floating() {
        assertThat(format("Знакоместа в начале числа: %15f %n", 9.8)).isEqualTo("Знакоместа в начале числа:        9.800000 \n");
        assertThat(format("Ведущие нули: %010f %n", 9.8)).isEqualTo("Ведущие нули: 009.800000 \n");
        assertThat(format("Ведущий ноль и десятичные разряды : %01.2f %n", .14)).isEqualTo("Ведущий ноль и десятичные разряды : 0.14 \n");
        assertThat(format("Вещественное число: %f%n", 3.14)).isEqualTo("Вещественное число: 3.140000\n");
        assertThat(format("Десятичные разряды: %.2f%n", 3.14)).isEqualTo("Десятичные разряды: 3.14\n");
        assertThat(format("Разделители групп разрядов (запятая): %,d%n", 1000000)).isEqualTo("Разделители групп разрядов (запятая): 1,000,000\n");
        assertThat(format(Locale.forLanguageTag("RU"), "Разделители групп разрядов (пробел): %,d%n", 1000000)).isEqualTo("Разделители групп разрядов (пробел): 1 000 000\n");

        // Two or less decimal digits
        var nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(0);

        assertThat(nf.format(2.3456)).isEqualTo("2.35");
        assertThat(nf.format(2.3)).isEqualTo("2.3");
        assertThat(nf.format(2)).isEqualTo("2");
    }

    @Test
    void escapeCharacter() {
        assertThat(format("Percentage: %.2f%%%n", 1.5)).isEqualTo("Percentage: 1.50%\n");
    }
}
