package nio.buffer;

import org.junit.jupiter.api.Test;

import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ByteBufferTest {

    @Test
    void allocate() {
        var bytes = "abc".getBytes();
        var cb = ByteBuffer.allocate(3);
        cb.put(bytes);
        assertThat(cb.array()).isEqualTo(bytes);
    }

    @Test
    void bufferOverflowException() {
        assertThatThrownBy(() -> ByteBuffer.allocate(2).put(new byte[]{1, 2, 3}))
                .isInstanceOf(BufferOverflowException.class);
    }
}
