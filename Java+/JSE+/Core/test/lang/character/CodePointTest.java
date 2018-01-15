package lang.character;

import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class CodePointTest {

    @Test
    public void charToCodePoint() {
        char ch1, ch2;
        ch1 = '\ud800';
        ch2 = '\udc00';
        int codePoint = Character.toCodePoint(ch1, ch2);
        assertThat(codePoint, equalTo(65536));
    }

    @Test
    public void codePointToChar() {
        char codePoint = '\u0041';
        assertThat(codePoint, equalTo('A'));
    }
}
