package lang.generics;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Присваивание значений обобщенным переменным.
 */
public class Assignment {
    @Test
    public void assign() {
        List<? extends Number> numbers = new ArrayList<>();
        List<Integer> integers = new ArrayList<>();
        numbers = integers;
    }
}
