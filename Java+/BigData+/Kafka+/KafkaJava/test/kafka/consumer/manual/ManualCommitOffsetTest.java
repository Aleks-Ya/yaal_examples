package kafka.consumer.manual;

import kafka.BaseTest;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import scala.jdk.javaapi.CollectionConverters;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import static java.util.Collections.singleton;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Commit offset manually (manual partition assignment with {@link KafkaConsumer#assign}).
 */
class ManualCommitOffsetTest extends BaseTest {

    @Test
    @Timeout(10)
    void commitOffset() throws ExecutionException, InterruptedException {
        var topic = ManualCommitOffsetTest.class.getSimpleName().toLowerCase();
        var value = 42;

        createTopic(topic, 1, 1, new Properties());

        produceIntegers(topic, value);

        // Consume the record, but not commit offset.
        var records1 = consumeByNewConsumer(topic, false);
        assertThat(records1.count()).isEqualTo(1);
        assertThat(records1.iterator().next().value()).isEqualTo(value);

        // Consume the record again (it was not committed), and commit.
        var records2 = consumeByNewConsumer(topic, true);
        assertThat(records2.count()).isEqualTo(1);
        assertThat(records2.iterator().next().value()).isEqualTo(value);

        // Try to consume the record again (cannot consume, because it was committed).
        var records3 = consumeByNewConsumer(topic, false);
        assertThat(records3).isEmpty();
    }

    private ConsumerRecords<String, Integer> consumeByNewConsumer(String topic, boolean commit) {
        Deserializer<String> keyDeserializer = new StringDeserializer();
        Deserializer<Integer> valueDeserializer = new IntegerDeserializer();
        var configsToRemove = CollectionConverters.asScala(Collections.<String>emptyList()).toList();
        var configOverrides = new Properties();
        configOverrides.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        try (Consumer<String, Integer> consumer = createConsumer(keyDeserializer, valueDeserializer, configOverrides, configsToRemove)) {
            var tp = new TopicPartition(topic, 0);
            consumer.assign(singleton(tp));
            var records = consumer.poll(Duration.ofSeconds(1));
            if (commit) {
                consumer.commitSync();
            }
            return records;
        }
    }

    @Override
    public int brokerCount() {
        return 1;
    }
}