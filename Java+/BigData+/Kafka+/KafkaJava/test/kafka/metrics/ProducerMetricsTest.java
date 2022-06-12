package kafka.metrics;

import kafka.BaseTest;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

class ProducerMetricsTest extends BaseTest {
    private static final String BUFFER_AVAILABLE_BYTES_METRIC_NAME = "buffer-available-bytes";
    private static final String RECORD_SEND_TOTAL_METRIC_NAME = "record-send-total";

    @Test
    @Timeout(10)
    void recordSendTotal() throws ExecutionException, InterruptedException {
        var topic = "topic";
        createTopic(topic, 1, 1, new Properties());
        try (Producer<String, String> producer = createProducer(new StringSerializer(), new StringSerializer(), new Properties())) {
            assertThat(getMetricByName(producer, RECORD_SEND_TOTAL_METRIC_NAME), equalTo(0D));
            var record = new ProducerRecord<String, String>(topic, "abc");
            producer.send(record).get();
            assertThat(getMetricByName(producer, RECORD_SEND_TOTAL_METRIC_NAME), equalTo(1D));
        }
    }

    @Test
    @Timeout(10)
    void bufferAvailableBytes() throws ExecutionException, InterruptedException {
        var topic = "topic";
        createTopic(topic, 1, 1, new Properties());
        try (Producer<String, String> producer = createProducer(new StringSerializer(), new StringSerializer(), new Properties())) {
            Double bufferAvailableBytes1 = getMetricByName(producer, BUFFER_AVAILABLE_BYTES_METRIC_NAME);

            var recordNum = 100;
            var futures = IntStream.range(0, recordNum)
                    .mapToObj(i -> new ProducerRecord<String, String>(topic, "abc"))
                    .map(producer::send)
                    .toList();
            Double bufferAvailableBytes2 = getMetricByName(producer, BUFFER_AVAILABLE_BYTES_METRIC_NAME);
            assertThat(bufferAvailableBytes2, lessThan(bufferAvailableBytes1));

            for (var future : futures) {
                future.get();
            }

            Double bufferAvailableBytes3 = getMetricByName(producer, BUFFER_AVAILABLE_BYTES_METRIC_NAME);
            assertThat(bufferAvailableBytes3, equalTo(bufferAvailableBytes1));
        }
    }

    private static <T> T getMetricByName(Producer<String, String> producer, String metricName) {
        return (T) producer.metrics().entrySet()
                .stream().filter(entry -> "producer-metrics".equals(entry.getKey().group()))
                .filter(entry -> metricName.equals(entry.getKey().name()))
                .findFirst().get().getValue().metricValue();
    }

    @Override
    public int brokerCount() {
        return 1;
    }
}