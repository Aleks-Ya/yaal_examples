import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ArrayUtilsTest {
    @Test
    void isEmpty() {
        var arr = new int[]{100, 200};
        assertThat(ArrayUtils.isEmpty(arr)).isFalse();
    }

    @Test
    void insert() {
        var a1 = new Integer[]{1, 3, 5, 7, 9};
        var a2 = ArrayUtils.insert(2, a1, 100);
        assertThat(a2).isEqualTo(new Integer[]{1, 3, 100, 5, 7, 9});
    }

    @Test
    void shift() {
        var arr = new Integer[]{1, 3, 5, 7, 9};
        ArrayUtils.shift(arr, 2);
        assertThat(arr).isEqualTo(new Integer[]{7, 9, 1, 3, 5});
    }

    @Test
    void shift2() {
        var arr = new Integer[]{1, 2, 3, 4, 5, 6, 7};
        ArrayUtils.shift(arr, 1, 4, 2);
        assertThat(arr).isEqualTo(new Integer[]{1, 3, 4, 2, 5, 6, 7});
    }
}