package lang.comparable;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CompareTest {
    @Test
    void numbers() {
        var i = Integer.valueOf(100);
        assertThat(i.compareTo(100)).isEqualTo(0);
        assertThat(i.compareTo(99)).isEqualTo(1);
        assertThat(i.compareTo(101)).isEqualTo(-1);
    }
}
