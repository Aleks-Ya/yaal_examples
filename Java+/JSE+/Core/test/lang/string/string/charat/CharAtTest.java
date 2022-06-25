package lang.string.string.charat;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class CharAtTest {

    @Test
    void substring() {
        var s = "0123";
        assertThat(s.charAt(1)).isEqualTo('1');
    }

    @Test
    void substringException1() {
        assertThatThrownBy(() -> "0".charAt(1)).isInstanceOf(StringIndexOutOfBoundsException.class);
    }
}