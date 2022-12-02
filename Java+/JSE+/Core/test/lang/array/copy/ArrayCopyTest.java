package lang.array.copy;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Копирование массивов.
 */
class ArrayCopyTest {
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
    void normal() {
        print("bigNums=", bigNums);
        print("littleNums=", littleNums);

        System.arraycopy(bigNums, 2, littleNums, 4, 2);
        print("bigNums=", bigNums);
        print("littleNums=", littleNums);
    }

    @Test
    void npe() {
        assertThatThrownBy(() -> System.arraycopy(null, 1, littleNums, 1, 1))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void arrayStoreException() {
        assertThatThrownBy(() -> {
            Integer[] intArray = new Integer[]{1, 2, 3, 4, 5};
            System.arraycopy(bigNums, 1, intArray, 1, 1);
        }).isInstanceOf(ArrayStoreException.class);
    }

    @Test
    void indexOutOfBoundsException() {
        assertThatThrownBy(() -> System.arraycopy(bigNums, 2, littleNums, 4, 20))
                .isInstanceOf(IndexOutOfBoundsException.class);
    }

}