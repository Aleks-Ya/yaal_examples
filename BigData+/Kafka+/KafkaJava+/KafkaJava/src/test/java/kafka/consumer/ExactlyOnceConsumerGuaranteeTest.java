package kafka.consumer;

import kafka.Utils;
import kafka.api.IntegrationTestHarness;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

/**
 * Implement "exactly once" consumer guarantee by committing each processed record individually.
 */
public class ExactlyOnceConsumerGuaranteeTest extends IntegrationTestHarness {

    @Test(timeout = 10_000)
    public void consume() throws ExecutionException, InterruptedException {
        String topic = "topic";
        createTopic(topic, 1, 1, new Properties());

        Integer expValue1 = 42;
        Integer expValue2 = 43;
        produce(topic, expValue1, expValue2);

        try (Consumer<String, Integer> consumer = createConsumer()) {
            java.util.List<ConsumerRecord<String, Integer>> recordList = consumeRecords(topic, consumer);
            assertThat(recordList, hasSize(2));

            ConsumerRecord<String, Integer> record1 = recordList.get(0);
            Integer actValue1 = record1.value();
            assertThat(actValue1, equalTo(expValue1));

            commitOffset(topic, consumer, record1);
        }
        try (Consumer<String, Integer> consumer = createConsumer()) {
            java.util.List<ConsumerRecord<String, Integer>> recordList = consumeRecords(topic, consumer);
            assertThat(recordList, hasSize(1));

            ConsumerRecord<String, Integer> record1 = recordList.get(0);
            Integer actValue1 = record1.value();
            assertThat(actValue1, equalTo(expValue2));

            commitOffset(topic, consumer, record1);
        }
        try (Consumer<String, Integer> consumer = createConsumer()) {
            java.util.List<ConsumerRecord<String, Integer>> recordList = consumeRecords(topic, consumer);
            assertThat(recordList, empty());
        }
    }

    private void commitOffset(String topic, Consumer<String, Integer> consumer, ConsumerRecord<String, Integer> record) {
        Map<TopicPartition, OffsetAndMetadata> offsets = new HashMap<>();
        TopicPartition partition = new TopicPartition(topic, record.partition());
        long nextOffset = record.offset() + 1;
        OffsetAndMetadata offset = new OffsetAndMetadata(nextOffset);
        offsets.put(partition, offset);
        consumer.commitSync(offsets);
    }

    private static java.util.List<ConsumerRecord<String, Integer>> consumeRecords(String topic,
                                                                                  Consumer<String, Integer> consumer) {
        consumer.subscribe(Collections.singleton(topic));
        ConsumerRecords<String, Integer> records = consumer.poll(Duration.ofSeconds(1));
        Iterable<ConsumerRecord<String, Integer>> topicRecords = records.records(topic);
        return Utils.iterableToList(topicRecords);
    }

    private void produce(String topic, Integer... values) throws InterruptedException, ExecutionException {
        Serializer<String> keySerializer = new StringSerializer();
        Serializer<Integer> valueSerializer = new IntegerSerializer();
        try (Producer<String, Integer> producer = createProducer(keySerializer, valueSerializer, new Properties())) {
            for (Integer value : values) {
                ProducerRecord<String, Integer> record = new ProducerRecord<>(topic, value);
                producer.send(record).get();
            }
        }
    }

    private Consumer<String, Integer> createConsumer() {
        Deserializer<String> keyDeserializer = new StringDeserializer();
        Deserializer<Integer> valueDeserializer = new IntegerDeserializer();
        List<String> configsToRemove = CollectionConverters.asScala(Collections.<String>emptyList()).toList();
        Properties configOverrides = new Properties();
        configOverrides.put(ConsumerConfig.GROUP_ID_CONFIG, "group1");
        configOverrides.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        return createConsumer(keyDeserializer, valueDeserializer, configOverrides, configsToRemove);
    }

    @Override
    public int brokerCount() {
        return 1;
    }
}