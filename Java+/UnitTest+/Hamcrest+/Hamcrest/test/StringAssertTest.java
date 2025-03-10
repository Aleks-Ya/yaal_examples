import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalToCompressingWhiteSpace;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.Matchers.stringContainsInOrder;

class StringAssertTest {

    @Test
    void strings() {
        final var str = "Людовико Эйнауди - Tu Sei";
        assertThat(str, containsString("Эйнауди"));
        assertThat(str, startsWith("Люд"));
        assertThat(str, endsWith("Tu Sei"));
        assertThat(23.4, hasToString("23.4"));
        assertThat(null, emptyOrNullString());
        assertThat("Gangalee Step", anyOf(containsString("Ganga"), containsString("No")));
        assertThat("", emptyString());
        assertThat("Jamaica Kingston Portion", stringContainsInOrder(asList("Jam", "King", "Port")));
        assertThat("Gangalee", equalToIgnoringCase("GaNgAlEe"));
        assertThat("   my\tfoo  bar ", equalToCompressingWhiteSpace(" my  foo bar"));
        assertThat("   my\tfoo\ntwo  bar ", equalToCompressingWhiteSpace(" my  foo two bar"));
        assertThat(new Object(), hasToString(startsWith("java.lang.Object@")));
    }

    @Test
    void regExp() {
        assertThat("obsolete hede", matchesPattern("^.*ete.*$"));
    }

    /**
     * Assert strings that contain variable substring.
     * E.g.: "Id:{any-id}"
     */
    @Test
    void anySubstring() {
        var matcher = matchesPattern("^Hello, \\w+!$");
        assertThat("Hello, John!", matcher);
        assertThat("Hello, Mary!", matcher);
    }
}