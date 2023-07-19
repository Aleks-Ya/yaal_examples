package util.concurrent.exchanger;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.Exchanger;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

class ExchangerTest {

    @Test
    void exchange() {
        var exchanger = new Exchanger<String>();
        final var limit = 3;
        final var output1 = new ArrayList<String>();
        final var output2 = new ArrayList<String>();
        var c1 = new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                for (var i = 0; i < limit; i++) {
                    var c2Value = exchanger.exchange("From-T1-" + i);
                    output1.add(c2Value);
                }
                return null;
            }
        };
        var c2 = new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                for (var i = 0; i < limit; i++) {
                    var c1Value = exchanger.exchange("From-T2-" + i);
                    output2.add(c1Value);
                }
                return null;
            }
        };

        var executor = Executors.newFixedThreadPool(2);
        executor.submit(c1);
        executor.submit(c2);
        await().until(() -> output1.size() == limit);
        assertThat(output1).containsExactly("From-T2-0", "From-T2-1", "From-T2-2");
        assertThat(output2).containsExactly("From-T1-0", "From-T1-1", "From-T1-2");
    }


}