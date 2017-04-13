import org.junit.Test;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * Проверка массивов.
 */
public class StringAssert {
    @Test
    public void strings() {
        final String str = "Людовико Эйнауди - Tu Sei";
        assertThat(str, containsString("Эйнауди"));
        assertThat(str, startsWith("Люд"));
        assertThat(str, endsWith("Tu Sei"));
        assertThat(23.4, hasToString("23.4"));
        assertThat(null, emptyOrNullString());
        assertThat("Gangalee Step", anyOf(containsString("Ganga"), containsString("No")));
        assertThat("", emptyString());
        assertThat("Jamaica Kingston Portion", stringContainsInOrder(asList("Jam", "King", "Port")));
        assertThat("Gangalee", equalToIgnoringCase("GaNgAlEe"));
        assertThat("Gangalee Step", equalToIgnoringWhiteSpace("GaNgAlEe\nStep\t"));
        assertThat(new Object(), hasToString(startsWith("java.lang.Object@")));
    }

    @Test
    public void regExp() {
        assertThat("obsolete hede", matchesPattern("^.*ete.*$"));
    }
}