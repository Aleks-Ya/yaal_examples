package string.string_builder.exercise;

import org.junit.Test;

import static java.lang.System.out;


/**
 * Ежегодный прирост рыбы в пруду составляет 15%. 
 * Запасы рыбы оценены в A тонн. 
 * Ежегодный план отлова  B тонн. 
 * Наименьший запас рыбы, ниже которого запас уже не восстанавливается, 
 * составляет С тонн. 
 * Составить алгоритм и программу, подсчитывающую, 
 * сколько лет можно выдержать заданный план.
 */ 
public class Fish {
    @Test
    public void main() {
		final float  yearIncreasePercent = 0.15f;
		final double startValue = 5_000;
		final double yearCatchValue = 1_000;
		final double deadValue = 500;
		
		int year = 0;
		double yearFinishValue = startValue;
		do {
			year++;
			double yearIncreaseValue = yearFinishValue * yearIncreasePercent;
			out.printf("Year: %d, Year Increase: %.2f, Finish Value: %.0f%n", year, yearIncreaseValue, yearFinishValue);
			yearFinishValue = yearFinishValue + yearIncreaseValue - yearCatchValue;
		} while(yearFinishValue >= deadValue);
		out.printf("Year: %d%n", year);
    }
}
