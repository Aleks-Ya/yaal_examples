import org.junit.Test;

import java.util.regex.Pattern;

import static org.junit.Assert.assertTrue;

/**
 * @author Aleksey Yablokov
 */
public class PredefinedSymbolClasses {
    @Test
    public void digits() throws Exception {
        assertTrue(Pattern.matches("a\\d+b", "a567b"));
    }
}
