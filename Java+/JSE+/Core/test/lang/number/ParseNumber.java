package lang.number;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class ParseNumber {
    @Test
    void parseDouble() {
        String s = "2234.2641";
        Double d = Double.parseDouble(s);
        assertThat(d, equalTo(2234.2641d));
    }
}
