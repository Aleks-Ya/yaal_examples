package lang.character;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CodePointTest {

    @Test
    public void charToCodePoint() {
        char ch1, ch2;
        ch1 = '\ud800';
        ch2 = '\udc00';
        int codePoint = Character.toCodePoint(ch1, ch2);
        assertThat(codePoint).isEqualTo(65536);
    }

    @Test
    void codePointToChar() {
        char codePoint = '\u0041';
        assertThat(codePoint).isEqualTo('A');
    }
}
