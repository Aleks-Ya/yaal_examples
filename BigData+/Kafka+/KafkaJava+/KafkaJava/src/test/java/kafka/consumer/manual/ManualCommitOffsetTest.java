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
import org.junit.Test;
import scala.collection.immutable.List;
import scala.jdk.javaapi.CollectionConverters;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import static java.util.Collections.singleton;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

/**
 * Commit offset manually (manual partition assignment with {@link KafkaConsumer#assign}).
 */
public class ManualCommitOffsetTest extends BaseTest {

    @Test(timeout = 10_000)
    public void commitOffset() throws ExecutionException, InterruptedException {
        String topic = ManualCommitOffsetTest.class.getSimpleName().toLowerCase();
        Integer value = 42;

        createTopic(topic, 1, 1, new Properties());

        produceIntegers(topic, value);

        // Consume the record, but not commit offset.
        ConsumerRecords<String, Integer> records1 = consumeByNewConsumer(topic, false);
        assertThat(records1.count(), equalTo(1));
        assertThat(records1.iterator().next().value(), equalTo(value));

        // Consume the record again (it was not committed), and commit.
        ConsumerRecords<String, Integer> records2 = consumeByNewConsumer(topic, true);
        assertThat(records2.count(), equalTo(1));
        assertThat(records2.iterator().next().value(), equalTo(value));

        // Try to consume the record again (cannot consume, because it was committed).
        ConsumerRecords<String, Integer> records3 = consumeByNewConsumer(topic, false);
        assertThat(records3.isEmpty(), is(true));
    }

    private ConsumerRecords<String, Integer> consumeByNewConsumer(String topic, boolean commit) {
        Deserializer<String> keyDeserializer = new StringDeserializer();
        Deserializer<Integer> valueDeserializer = new IntegerDeserializer();
        List<String> configsToRemove = CollectionConverters.asScala(Collections.<String>emptyList()).toList();
        Properties configOverrides = new Properties();
        configOverrides.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        try (Consumer<String, Integer> consumer = createConsumer(keyDeserializer, valueDeserializer, configOverrides, configsToRemove)) {
            TopicPartition tp = new TopicPartition(topic, 0);
            consumer.assign(singleton(tp));
            ConsumerRecords<String, Integer> records = consumer.poll(Duration.ofSeconds(1));
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