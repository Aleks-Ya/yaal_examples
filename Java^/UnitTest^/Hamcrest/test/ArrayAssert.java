import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.arrayContainingInAnyOrder;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.Matchers.emptyArray;
import static org.hamcrest.Matchers.hasItemInArray;
import static org.junit.Assert.assertThat;

/**
 * Проверка массивов.
 */
public class ArrayAssert {
    @Test
    public void objectArray() {
        final Integer[] arr = {45, 34, 89};
        assertThat(arr, hasItemInArray(34));
        assertThat(arr, arrayContaining(45, 34, 89));//в такой же последовательности
        assertThat(arr, arrayContainingInAnyOrder(89, 34, 45));
        assertThat(arr, arrayWithSize(3));
        assertThat(new Integer[]{}, emptyArray());
    }

    @Test
    @Ignore("Падает с ошибкой из-за того, что Mockito тянет старый Harmcrest 1.1")
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