package util.regex;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Замена подстроки с помощью Matcher.
 */
public class MatcherReplaceTest {
    @Test
    public void replaceGroup() {
        var source = "GET /abba HTTP/1.1/abba";
        var p = Pattern.compile("(/abba)");
        var m = p.matcher(source);
        var sb = new StringBuffer(source.length());
        while (m.find()) {
            var text = m.group();
            text = text.toUpperCase();
            m.appendReplacement(sb, Matcher.quoteReplacement(text));
        }
        m.appendTail(sb);
        assertThat(sb.toString(), equalTo("GET /ABBA HTTP/1.1/ABBA"));
    }
}