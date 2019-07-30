import org.junit.Test;

import java.util.Collections;
import java.util.Enumeration;

import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertThat;

/**
 * Проверка объектов {@link Enumeration}.
 */
public class EnumerationAssert {

    @Test
    public void test() {
        Enumeration<String> enumeration = Collections.emptyEnumeration();
        assertThat(Collections.list(enumeration), empty());
    }
}