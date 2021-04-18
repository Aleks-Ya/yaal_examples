import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Enumeration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;

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