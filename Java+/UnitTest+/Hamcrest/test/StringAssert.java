import org.junit.Test;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.equalToIgnoringWhiteSpace;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.Matchers.stringContainsInOrder;
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
        //не работает из-за того, что mockito тянет старый harmcrest 1.1
//        assertThat(null, isEmptyOrNullString());
//        assertThat("Gangalee Step", anyOf(containsString("Ganga"), containsString("No")));
        assertThat("", isEmptyString());
        assertThat("Jamaica Kingston Portion", stringContainsInOrder(asList("Jam", "King", "Port")));
        assertThat("Gangalee", equalToIgnoringCase("GaNgAlEe"));
        assertThat("Gangalee Step", equalToIgnoringWhiteSpace("GaNgAlEe\nStep\t"));
    }

}