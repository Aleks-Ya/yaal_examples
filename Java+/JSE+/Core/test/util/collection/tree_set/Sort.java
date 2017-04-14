package util.collection.tree_set;

import java.util.Set;
import java.util.TreeSet;

/**
 * Сортировка элементов карты с помощью интерфейса Comparable.
 */
public class Sort {

    public static void main(String[] args) {
		Bank b3 = new Bank(3);
		Bank b2 = new Bank(2);
		Bank b1 = new Bank(1);
		
		Set<Bank> banks = new TreeSet<>();
		banks.add(b3);
		banks.add(b2);
		banks.add(b1);
		
		for(Bank bank : banks) {
			System.out.println(bank);
		}
    }
}

class Bank implements Comparable<Bank> {
	private int number;
	
	Bank(int number) {
		this.number = number;
	}
	
	@Override
	public int compareTo(Bank o) {
		if (number == o.number) {
			return 0;
		} else if (number < o.number) {
			return -1;
		} else {
			return 1;
		}
	}
	
	@Override
	public String toString() {
		return "Bank N" + number;
	}
}
