package rule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.startsWith;

/**
 * Используем правило ErrorCollector для сбора всех ошибок
 * во время теста и вывода их разом.
 */
public class ErrorCollectorRule {

    @Rule
    public final ErrorCollector collector = new ErrorCollector();

    @Test
    public void testName() throws Exception {
        List<String> rows = Arrays.asList(
                "Row1",
                "Wrong row",
                "Row2",
                "Unexpected row"
        );

        for (String row : rows) {
            collector.checkThat(row, startsWith("Row"));
        }
    }
}
