package kafka.partitioner;

import kafka.api.IntegrationTestHarness;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Using custom {@link Partitioner}.
 */
class PartitionerTest extends IntegrationTestHarness {
    private static final String topic = PartitionerTest.class.getSimpleName().toLowerCase();

    public static class StringFirstCharPartitioner implements Partitioner {
        public static final String DELIMITER_CHAR_PROPERTY = "delimiter.char";
        private char delimiterChar = 'K';

        @Override
        public int partition(String topic,
                             Object key,
                             byte[] keyBytes,
                             Object value,
                             byte[] valueBytes,
                             Cluster cluster) {
            var str = (String) value;
            if (str == null || str.isEmpty()) {
                return 0;
            }
            return str.charAt(0) < delimiterChar ? 1 : 2;
        }

        @Override
        public void close() {
            //nothing
        }

        @Override
        public void configure(Map<String, ?> configs) {
            var delimiterCharStr = (String) configs.get(DELIMITER_CHAR_PROPERTY);
            if (delimiterCharStr == null) {
                return;
            }
            if (delimiterCharStr.length() != 1) {
                throw new IllegalArgumentException("Wrong delimiter char: " + delimiterCharStr);
            }
            delimiterChar = delimiterCharStr.charAt(0);
        }
    }

    @Test
    @Timeout(10)
    void partitioner() throws ExecutionException, InterruptedException {
        var value1 = "Alphabet";
        var value2 = "Zoo";
        var value0 = "";

        createTopic(topic, 3, 1, new Properties());

        var producerConfig = new Properties();
        producerConfig.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, StringFirstCharPartitioner.class.getName());

        Serializer<String> keySerializer = new StringSerializer();
        Serializer<String> valueSerializer = new StringSerializer();
        try (Producer<String, String> producer = createProducer(keySerializer, valueSerializer, producerConfig)) {

            var partition1 = producer.send(new ProducerRecord<>(topic, value1)).get().partition();
            assertThat(partition1, equalTo(1));

            var partition2 = producer.send(new ProducerRecord<>(topic, value2)).get().partition();
            assertThat(partition2, equalTo(2));

            var partition0 = producer.send(new ProducerRecord<>(topic, value0)).get().partition();
            assertThat(partition0, equalTo(0));

        }
    }

    @Override
    public int brokerCount() {
        return 3;
    }
}