package nio.buffer;

import org.junit.jupiter.api.Test;

import java.nio.CharBuffer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CharBufferTest {
    @Test
    public void allocate() {
        var cb = CharBuffer.allocate(3);
        var data = "abc";
        cb.put(data);
        assertThat(new String(cb.array()), equalTo(data));
    }
}
