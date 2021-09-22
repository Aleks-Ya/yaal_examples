package lang.string.string_builder.exercise;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;

/**
 * Банк начисляет на вклады 5% каждый месяц. 
 * Напечатать таблицу увеличения суммы вклада по месяцам на год. 
 * Сумма вклада вводится с клавиатуры
 */ 
class BankInterest {
    @Test
    void main() {
		final float percent = 0.01f;
		final double startAmount = 500_000;
		final int months = 12;
		
		double amount = startAmount;
		for (int m = 0; m < months; m++) {
			amount *= 1 + percent;
		}
		
		out.printf("Start sum: %.2f%n", startAmount);
		out.printf("Period: %d months%n", months);
		out.printf("Month percent: %.0f%%%n", percent * 100);
		out.printf("Finish sum: %.2f%n", amount);
    }
}
