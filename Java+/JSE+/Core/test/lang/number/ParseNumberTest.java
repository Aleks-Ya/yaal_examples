package lang.number;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ParseNumberTest {
    @Test
    void parseDouble() {
        var s = "2234.2641";
        Double d = Double.parseDouble(s);
        assertThat(d).isEqualTo(2234.2641d);
    }
}
