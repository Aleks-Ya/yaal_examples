package nio.buffer;

import org.junit.jupiter.api.Test;

import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThrows;

public class ByteBufferTest {

    @Test
    public void allocate() {
        var bytes = "abc".getBytes();
        var cb = ByteBuffer.allocate(3);
        cb.put(bytes);
        assertThat(cb.array(), equalTo(bytes));
    }

    @Test
    public void bufferOverflowException() {
        assertThrows(BufferOverflowException.class, () -> ByteBuffer.allocate(2).put(new byte[]{1, 2, 3}));
    }
}
