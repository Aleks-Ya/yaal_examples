package lang.character;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NonBreakSpaceTest {

    @Test
    void space() {
        char nonBreakSpace = '\u00A0';
        var str = "a" + nonBreakSpace + "b";
        assertThat(str).isEqualTo("a b");
    }
}
