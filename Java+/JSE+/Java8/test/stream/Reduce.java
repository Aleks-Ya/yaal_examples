package stream;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Аггрегирующая операция Stream#reduce.
 */
public class Reduce {
    private final List<Integer> list = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        list.add(1);
        list.add(2);
        list.add(3);
    }

    /**
     * Сумма.
     */
    @Test
    public void sum() {
        int sum = list.stream().mapToInt(Integer::intValue).reduce(0, (left, right) -> left + right);
        assertThat(sum, equalTo(6));
    }
}
