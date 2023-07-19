package util.concurrent.collection;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedTransferQueue;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

class TransferQueueTest {

    @Test
    void transfer() {
        final var limit = 5;
        final var output = new ArrayList<Integer>();
        final var queue = new LinkedTransferQueue<Integer>();
        var c1 = new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                for (var i = 0; i < limit; i++) {
                    queue.transfer(i);
                }
                return null;
            }
        };
        var c2 = new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                var counter = 0;
                while (counter < limit) {
                    var number = queue.take();
                    output.add(number);
                    counter++;
                }
                return null;
            }
        };

        var executor = Executors.newFixedThreadPool(2);
        executor.submit(c1);
        executor.submit(c2);
        await().until(() -> output.size() == limit);
        assertThat(output).containsExactly(0, 1, 2, 3, 4);
    }


}