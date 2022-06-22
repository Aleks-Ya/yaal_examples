package nio.buffer;

import org.junit.jupiter.api.Test;

import java.nio.CharBuffer;

import static org.assertj.core.api.Assertions.assertThat;

class CharBufferTest {
    @Test
    void allocate() {
        var cb = CharBuffer.allocate(3);
        var data = "abc";
        cb.put(data);
        assertThat(new String(cb.array())).isEqualTo(data);
    }
}
