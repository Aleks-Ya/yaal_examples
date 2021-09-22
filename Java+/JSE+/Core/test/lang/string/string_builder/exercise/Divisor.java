package lang.string.string_builder.exercise;

import org.junit.jupiter.api.Test;

/**
 * Составить программу, которая печатает все различные представления 
 * числа N в виде всевозможных произведений (сумм) K натуральных чисел 
 * (N, K-вводятся, 1<K<N ). Если К=0, то выдать все возможные 
 * произведения (суммы). Представления чисел, отличающихся только 
 * порядком сомножителей (слагаемых), считаются одинаковыми.
 */ 
class Divisor {
    @Test
    void main() {
		DivisorCalculator c = new DivisorCalculator(76, 3);
    }
}

class DivisorCalculator {
	
	/**
	 * Число, для которого определяем множители и слагаемые.
	 */
	private final int num;
	
	/**
	 * Количество множителей или слагаемых.
	 */
	private final int count;
	
	/**
	 * Множители.
	 */
	private final int[][] multipliers = null;
	
	/**
	 * Слагаемые.
	 */
	private final int[][] items = null;

	DivisorCalculator(int num, int count) {
		this.num = num;
		this.count = count;
		if (count < 0) {
			throw new IllegalArgumentException("Количество множителей/слагаемых меньше 0.");
		}
		if (count >= num) {
			throw new IllegalArgumentException("Количество множителей/слагаемых не может быть больше числа.");
		}
		//multipliers = new int[count];
		//items = new int[count];
		calculate();
	}
	
	private void calculate() {
		
		for(int k = 0; k < count; k++) {
			
		}
	}
	
	int[][] multipliers() {
		return multipliers;
	}
	
	int[][] items() {
		return items;
	}
	
}
