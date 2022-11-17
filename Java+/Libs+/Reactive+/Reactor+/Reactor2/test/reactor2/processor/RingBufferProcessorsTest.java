package reactor2.processor;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Processor;
import reactor.core.processor.RingBufferProcessor;
import reactor.rx.Streams;

/**
 * Использование RingBufferProcessor.
 */
class RingBufferProcessorsTest {
    @Test
    void fromDocumentation() throws InterruptedException {
        Processor<Integer, Integer> p = RingBufferProcessor.create("test", 32);
        var s = Streams.wrap(p);

        s.consume(i -> System.out.println(Thread.currentThread() + " data=" + i));
        s.consume(i -> System.out.println(Thread.currentThread() + " data=" + i));
        s.consume(i -> System.out.println(Thread.currentThread() + " data=" + i));
        //ничего не выводит - не дописан
    }
}
