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
    public void testName() {
        final Integer[] arr = {45, 34, 89};
        assertThat(arr, hasItemInArray(34));
        assertThat(arr, arrayContaining(45, 34, 89));//в такой же последовательности
        assertThat(arr, arrayContainingInAnyOrder(89, 34, 45));
        assertThat(arr, arrayWithSize(3));
        assertThat(new Integer[]{}, emptyArray());
    }
}