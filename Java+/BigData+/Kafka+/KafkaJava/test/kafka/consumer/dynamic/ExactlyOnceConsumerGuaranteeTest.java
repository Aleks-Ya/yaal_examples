package kafka.consumer.dynamic;

import kafka.BaseTest;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import scala.jdk.javaapi.CollectionConverters;
import util.CollectionUtil;

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
class ExactlyOnceConsumerGuaranteeTest extends BaseTest {

    @Test
    @Timeout(10)
    void consume() throws ExecutionException, InterruptedException {
        var topic = "topic";
        createTopic(topic, 1, 1, new Properties());

        Integer expValue1 = 42;
        Integer expValue2 = 43;
        produceIntegers(topic, expValue1, expValue2);

        try (var consumer = createConsumer()) {
            var recordList = consumeRecords(topic, consumer);
            assertThat(recordList, hasSize(2));

            var record1 = recordList.get(0);
            var actValue1 = record1.value();
            assertThat(actValue1, equalTo(expValue1));

            commitOffset(topic, consumer, record1);
        }
        try (var consumer = createConsumer()) {
            var recordList = consumeRecords(topic, consumer);
            assertThat(recordList, hasSize(1));

            var record1 = recordList.get(0);
            var actValue1 = record1.value();
            assertThat(actValue1, equalTo(expValue2));

            commitOffset(topic, consumer, record1);
        }
        try (var consumer = createConsumer()) {
            var recordList = consumeRecords(topic, consumer);
            assertThat(recordList, empty());
        }
    }

    private void commitOffset(String topic, Consumer<String, Integer> consumer, ConsumerRecord<String, Integer> record) {
        Map<TopicPartition, OffsetAndMetadata> offsets = new HashMap<>();
        var partition = new TopicPartition(topic, record.partition());
        var nextOffset = record.offset() + 1;
        var offset = new OffsetAndMetadata(nextOffset);
        offsets.put(partition, offset);
        consumer.commitSync(offsets);
    }

    private static java.util.List<ConsumerRecord<String, Integer>> consumeRecords(String topic,
                                                                                  Consumer<String, Integer> consumer) {
        consumer.subscribe(Collections.singleton(topic));
        var records = consumer.poll(Duration.ofSeconds(1));
        var topicRecords = records.records(topic);
        return CollectionUtil.iterableToList(topicRecords);
    }

    private Consumer<String, Integer> createConsumer() {
        Deserializer<String> keyDeserializer = new StringDeserializer();
        Deserializer<Integer> valueDeserializer = new IntegerDeserializer();
        var configsToRemove = CollectionConverters.asScala(Collections.<String>emptyList()).toList();
        var configOverrides = new Properties();
        configOverrides.put(ConsumerConfig.GROUP_ID_CONFIG, "group1");
        configOverrides.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        return createConsumer(keyDeserializer, valueDeserializer, configOverrides, configsToRemove);
    }

    @Override
    public int brokerCount() {
        return 1;
    }
}