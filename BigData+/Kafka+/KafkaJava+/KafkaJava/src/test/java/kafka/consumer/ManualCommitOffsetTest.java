package kafka.consumer;

import kafka.api.IntegrationTestHarness;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Test;
import scala.collection.immutable.List;
import scala.jdk.javaapi.CollectionConverters;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

/**
 * Commit offset manually.
 */
public class ManualCommitOffsetTest extends IntegrationTestHarness {

    @Test(timeout = 10_000)
    public void commitOffset() throws ExecutionException, InterruptedException {
        String topic = ManualCommitOffsetTest.class.getSimpleName().toLowerCase();
        Integer value = 42;

        createTopic(topic, 1, 1, new Properties());

        produce(topic, value);

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

    private void produce(String topic, Integer value) throws InterruptedException, ExecutionException {
        Serializer<String> keySerializer = new StringSerializer();
        Serializer<Integer> valueSerializer = new IntegerSerializer();
        try (Producer<String, Integer> producer = createProducer(keySerializer, valueSerializer, new Properties())) {
            ProducerRecord<String, Integer> record = new ProducerRecord<>(topic, value);
            producer.send(record).get();
        }
    }

    private ConsumerRecords<String, Integer> consumeByNewConsumer(String topic, boolean commit) {
        Deserializer<String> keyDeserializer = new StringDeserializer();
        Deserializer<Integer> valueDeserializer = new IntegerDeserializer();
        List<String> configsToRemove = CollectionConverters.asScala(Collections.<String>emptyList()).toList();
        Properties configOverrides = new Properties();
        configOverrides.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        try (Consumer<String, Integer> consumer = createConsumer(keyDeserializer, valueDeserializer, configOverrides, configsToRemove)) {
            consumer.subscribe(Collections.singleton(topic));
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