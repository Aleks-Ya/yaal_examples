package copy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Копирование массивов.
 */
public class ArrayCopy {
    private final int[] bigNums = new int[]{12, 23, 34, 78, 89};
    private final int[] littleNums = new int[]{1, 2, 3, 4, 5, 6, 7};

    private static void print(String prefix, int[] arr) {
        System.out.print(prefix);
        for (int i : arr) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    @Test
    public void normal() {
        print("bigNums=", bigNums);
        print("littleNums=", littleNums);

        System.arraycopy(bigNums, 2, littleNums, 4, 2);
        print("bigNums=", bigNums);
        print("littleNums=", littleNums);
    }

    @Test
    public void npe() {
        assertThrows(NullPointerException.class, () -> System.arraycopy(null, 1, littleNums, 1, 1));
    }

    @Test
    public void arrayStoreException() {
        assertThrows(ArrayStoreException.class, () -> {
            Integer[] intArray = new Integer[]{1, 2, 3, 4, 5};
            System.arraycopy(bigNums, 1, intArray, 1, 1);
        });
    }

    @Test
    public void indexOutOfBoundsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> System.arraycopy(bigNums, 2, littleNums, 4, 20));
    }

}