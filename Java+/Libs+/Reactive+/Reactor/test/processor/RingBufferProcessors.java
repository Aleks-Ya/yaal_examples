package processor;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Processor;
import reactor.core.processor.RingBufferProcessor;
import reactor.rx.Stream;
import reactor.rx.Streams;

/**
 * Использование RingBufferProcessor.
 */
public class RingBufferProcessors {
    @Test
    void fromDocumentation() throws InterruptedException {
        Processor<Integer, Integer> p = RingBufferProcessor.create("test", 32);
        Stream<Integer> s = Streams.wrap(p);

        s.consume(i -> System.out.println(Thread.currentThread() + " data=" + i));
        s.consume(i -> System.out.println(Thread.currentThread() + " data=" + i));
        s.consume(i -> System.out.println(Thread.currentThread() + " data=" + i));
        //ничего не выводит - не дописан
    }
}
