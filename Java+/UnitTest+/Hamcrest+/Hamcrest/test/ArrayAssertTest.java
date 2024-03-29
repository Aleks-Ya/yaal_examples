import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.arrayContainingInAnyOrder;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.Matchers.emptyArray;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItemInArray;

/**
 * Проверка массивов.
 */
class ArrayAssertTest {

    @Test
    void equal() {
        final Integer[] arr1 = {1, 2, 3};
        final Integer[] arr2 = {1, 2, 3};
        assertThat(arr1, equalTo(arr2));
    }

    @Test
    void objectArray() {
        final Integer[] arr = {45, 34, 89};
        assertThat(arr, hasItemInArray(34));
        assertThat(arr, arrayContaining(45, 34, 89));//все элементы в такой же последовательности
        assertThat(new Character[]{'a', 'b'}, arrayContaining('a', 'b'));//в такой же последовательности
        assertThat(arr, arrayContainingInAnyOrder(89, 34, 45));//все элементы в любой последовательности
        assertThat(arr, arrayWithSize(3));
        assertThat(new Integer[]{}, emptyArray());
    }

    @Test
    @Disabled("Падает с ошибкой из-за того, что Mockito тянет старый Hamcrest 1.1")
    public void primitiveArray() {
        final int[] arr = {45, 34, 89};
        final Integer[] arrObj = toObjectArray(arr);
        assertThat(arrObj, hasItemInArray(34));
        assertThat(arrObj, arrayContaining(45, 34, 89));//в такой же последовательности
        assertThat(arrObj, arrayContainingInAnyOrder(89, 34, 45));
        assertThat(arrObj, arrayWithSize(3));
        assertThat(new Integer[]{}, emptyArray());
    }

    /**
     * Конвертация массива примитивов int[] в массив объектов Integer[]
     * необходима, п.ч. интерфейс матчер использует Generics
     * (не может принимать примитивы).
     */
    private Integer[] toObjectArray(int[] array) {
        Integer[] result = new Integer[array.length];
        for (int i = 0; i < array.length - 1; i++) {
            result[i] = array[i];
        }
        return result;
    }
}