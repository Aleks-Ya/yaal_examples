package util.collection.tree_set;

import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.TreeSet;

/**
 * Сортировка элементов карты с помощью интерфейса Comparable.
 */
class Sort {

    @Test
    void sort() {
        var b3 = new Bank(3);
        var b2 = new Bank(2);
        var b1 = new Bank(1);

        Set<Bank> banks = new TreeSet<>();
        banks.add(b3);
        banks.add(b2);
        banks.add(b1);

        for (var bank : banks) {
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
