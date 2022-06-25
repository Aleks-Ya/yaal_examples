package lang.generics;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Присваивание значений обобщенным переменным.
 */
class AssignmentTest {
    @Test
    void assign() {
        List<? extends Number> numbers = new ArrayList<>();
        List<Integer> integers = new ArrayList<>();
        numbers = integers;
    }
}
