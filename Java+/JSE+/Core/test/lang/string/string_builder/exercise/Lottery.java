package lang.string.string_builder.exercise;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Задача "Лотерея".
 * Заполнить коллекцию числами от 1 до 20.
 * Вытаскивать числа в случайной последовательности и выводить на экран.
 */
class Lottery {
    @Test
    void lottery() {
        final var size = 20;
        final List<Byte> nums = new ArrayList<>(size);
        for (byte i = 1; i <= size; i++) {
            nums.add(i);
            System.out.print(i + " ");
        }
        System.out.println();

        final var random = new Random();
        while (!nums.isEmpty()) {
            var index = random.nextInt(nums.size());
            byte b = nums.remove(index);
            System.out.print(b + " ");
        }
    }
}
