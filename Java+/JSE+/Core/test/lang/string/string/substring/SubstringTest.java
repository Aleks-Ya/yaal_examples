package lang.string.string.substring;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


/**
 * String#substring(int fromInclusive, int toExclusive)
 * String#substring(int fromInclusive)
 */
class SubstringTest {

    @Test
    void substring() {
        var s = "0123456789";
        assertThat(s.substring(1, 3)).isEqualTo("12");
        assertThat(s.substring(6)).isEqualTo("6789");
        assertThat(s.substring(10)).isEmpty();
    }

    @Test
    void substringException1() {
        assertThatThrownBy(() -> {
            var s = "0";
            assertThat(s.substring(1)).isEmpty();
            s.substring(2);
        }).isInstanceOf(StringIndexOutOfBoundsException.class);
    }

    @Test
    void substringException2() {
        assertThatThrownBy(() -> {
            var s = "012345";
            assertThat(s.substring(0, 6)).isEqualTo("012345");
            s.substring(0, 7);
        }).isInstanceOf(StringIndexOutOfBoundsException.class);
    }
}