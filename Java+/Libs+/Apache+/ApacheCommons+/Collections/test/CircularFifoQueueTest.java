import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CircularFifoQueueTest {
    private final CircularFifoQueue<String> queue = new CircularFifoQueue<>(List.of("a", "b", "c"));

    @Test
    void poll() {
        assertThat(queue.poll()).isEqualTo("a");
        assertThat(queue.poll()).isEqualTo("b");
        assertThat(queue.poll()).isEqualTo("c");
        assertThat(queue.poll()).isNull();
    }

    @Test
    void peek() {
        assertThat(queue.peek()).isEqualTo("a");
        assertThat(queue.peek()).isEqualTo("a");
    }

    @Test
    void remove() {
        assertThat(queue.remove()).isEqualTo("a");
        assertThat(queue.remove()).isEqualTo("b");
        assertThat(queue.remove()).isEqualTo("c");
        assertThatThrownBy(queue::remove).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void element() {
        assertThat(queue.element()).isEqualTo("a");
        assertThat(queue.element()).isEqualTo("a");
    }

    @Test
    void iterator() {
        var iterator = queue.iterator();
        assertThat(iterator.next()).isEqualTo("a");
        assertThat(iterator.next()).isEqualTo("b");
        assertThat(iterator.next()).isEqualTo("c");
        assertThatThrownBy(iterator::next).isInstanceOf(NoSuchElementException.class);
    }
}