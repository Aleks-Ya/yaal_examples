package util.regex;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Замена подстроки с помощью Matcher.
 */
class MatcherReplaceTest {
    @Test
    void replaceGroup() {
        var source = "GET /abba HTTP/1.1/abba";
        var p = Pattern.compile("(/abba)");
        var m = p.matcher(source);
        var sb = new StringBuilder(source.length());
        while (m.find()) {
            var text = m.group();
            text = text.toUpperCase();
            m.appendReplacement(sb, Matcher.quoteReplacement(text));
        }
        m.appendTail(sb);
        assertThat(sb.toString()).isEqualTo("GET /ABBA HTTP/1.1/ABBA");
    }
}