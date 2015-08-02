package deserialization;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Замена подстроки с помощью Matcher.
 */
public class MatcherReplace {
    @Test
    public void replaceGroup() {
        String source = "GET /abba HTTP/1.1/abba";
        Pattern p = Pattern.compile("(/abba)");
        Matcher m = p.matcher(source);
        StringBuffer sb = new StringBuffer(source.length());
        while (m.find()) {
            String text = m.group();
            text = text.toUpperCase();
            m.appendReplacement(sb, Matcher.quoteReplacement(text));
        }
        m.appendTail(sb);
        assertThat(sb.toString(), equalTo("GET /ABBA HTTP/1.1/ABBA"));
    }
}