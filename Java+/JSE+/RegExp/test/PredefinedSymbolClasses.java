import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Предопределенные символьные классы: \d
 */
public class PredefinedSymbolClasses {
    @Test
    public void digits() {
        assertTrue("a567b".matches("a\\d+b"));
    }
}
