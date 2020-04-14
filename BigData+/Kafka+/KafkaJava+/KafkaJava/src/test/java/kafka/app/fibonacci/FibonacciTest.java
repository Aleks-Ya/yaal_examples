package kafka.app.fibonacci;

import kafka.api.IntegrationTestHarness;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

/**
 * Integration test for {@link FibonacciProducer} and {@link FibonacciConsumer}.
 */
public class FibonacciTest extends IntegrationTestHarness {
    private static final Logger log = LoggerFactory.getLogger(FibonacciTest.class);
    private static final String topic = "fibonacci";

    @Test(timeout = 30_000)
    public void fibonacci() throws ExecutionException, InterruptedException {
        createTopic(topic, 1, 1, new Properties());

        int sendNumberCount = 8;
        FibonacciProducer producer = new FibonacciProducer(topic, sendNumberCount, producerConfig());
        producer.work();

        FibonacciConsumer consumer = new FibonacciConsumer(topic, 2, consumerConfig());
        int receiveNumberCount = 4;
        List<Long> fibonacciSums = new ArrayList<>();
        consumer.work(sum -> {
            fibonacciSums.add(sum);
            if (fibonacciSums.size() == receiveNumberCount) {
                consumer.stop();
            }
        });
        log.info("Received numbers: " + fibonacciSums);

        assertThat(fibonacciSums, contains(1L, 4L, 12L, 33L));
    }

    @Override
    public int brokerCount() {
        return 1;
    }
}