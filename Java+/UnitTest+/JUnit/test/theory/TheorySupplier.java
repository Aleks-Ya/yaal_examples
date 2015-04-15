package theory;

import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.experimental.theories.suppliers.TestedOn;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeTrue;

/**
 * Использование теорий JUnit (Theory).
 * Генерация входных данных с помощью поставщиков (supplier).
 * <p>
 * Ограничение: стандартный поставщик @TestedOn
 * принимает только int. Для поддержки других типов нужно
 * писать расширения org.junit.experimental.theories.ParameterSupplier
 */
@RunWith(Theories.class)
public class TheorySupplier {
    @Theory
    public void square(@TestedOn(ints = {4, 16, 64}) int n) {
        assumeTrue(n > 0);
        double sqrt = Math.sqrt(n);
        assertEquals(n, sqrt * sqrt, 1);
    }
}
