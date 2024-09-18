package kafka.consumer.dynamic;

import kafka.BaseTest;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import util.CollectionUtil;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.Collections.singleton;
import static org.assertj.core.api.Assertions.assertThat;
import static scala.jdk.javaapi.CollectionConverters.asScala;

/**
 * Consumer all new messages added to a topic after the last run.
 * (dynamic partition assignment with {@link KafkaConsumer#subscribe})
 */
class ConsumeAllNewMessagesTest extends BaseTest {

    @Test
    @Timeout(10)
    void consume() throws ExecutionException, InterruptedException {
        var topic = "topic";
        createTopic(topic, 1, 1, new Properties());

        //Consume first messages (#1 and #2)
        var expValue1 = 42;
        var expValue2 = 43;
        produceIntegers(topic, expValue1, expValue2);
        try (var consumer = createConsumer()) {
            assertThat(consumeRecords(consumer, topic)).contains(expValue1, expValue2);
            assertThat(getCommittedOffset(consumer, topic)).hasValue(2L);
            assertThat(getEndOffset(consumer, topic)).hasValue(2L);
        }

        //Consume new messages (#3 and #4)
        var expValue3 = 44;
        var expValue4 = 45;
        produceIntegers(topic, expValue3, expValue4);
        try (var consumer = createConsumer()) {
            assertThat(consumeRecords(consumer, topic)).contains(expValue3, expValue4);
            assertThat(getCommittedOffset(consumer, topic)).hasValue(4L);
            assertThat(getEndOffset(consumer, topic)).hasValue(4L);
        }

        //Consume no new messages
        try (var consumer = createConsumer()) {
            assertThat(consumeRecords(consumer, topic)).isEmpty();
            assertThat(getCommittedOffset(consumer, topic)).hasValue(4L);
            assertThat(getEndOffset(consumer, topic)).hasValue(4L);
        }

        //Consume new messages added between polls (#5, #6, #7, #8)
        var expValue5 = 46;
        var expValue6 = 47;
        produceIntegers(topic, expValue5, expValue6);
        try (var consumer = createConsumer()) {
            assertThat(consumeRecords(consumer, topic)).contains(expValue5, expValue6);
            assertThat(getCommittedOffset(consumer, topic)).hasValue(6L);
            assertThat(getEndOffset(consumer, topic)).hasValue(6L);

            var expValue7 = 48;
            var expValue8 = 49;
            produceIntegers(topic, expValue7, expValue8);
            assertThat(getCommittedOffset(consumer, topic)).hasValue(6L);
            assertThat(getEndOffset(consumer, topic)).hasValue(8L);
            assertThat(consumeRecords(consumer, topic)).contains(expValue7, expValue8);
            assertThat(getCommittedOffset(consumer, topic)).hasValue(8L);
            assertThat(getEndOffset(consumer, topic)).hasValue(8L);
        }
    }

    private List<Integer> consumeRecords(Consumer<String, Integer> consumer, String topic) {
        consumer.subscribe(singleton(topic));
        var records = consumer.poll(Duration.ofSeconds(1));
        consumer.commitSync();
        var topicRecords = records.records(topic);
        var recordList = CollectionUtil.iterableToList(topicRecords);
        return recordList.stream().map(ConsumerRecord::value).collect(Collectors.toList());
    }

    private Optional<Long> getCommittedOffset(Consumer<String, Integer> consumer, String topic) {
        consumer.subscribe(singleton(topic));
        var tp = new TopicPartition(topic, 0);
        var committed = consumer.committed(singleton(tp));
        if (committed.containsKey(tp)) {
            return Optional.of(committed.get(tp).offset());
        } else {
            return Optional.empty();
        }
    }

    private Optional<Long> getEndOffset(Consumer<String, Integer> consumer, String topic) {
        consumer.subscribe(singleton(topic));
        var tp = new TopicPartition(topic, 0);
        var endOffsets = consumer.endOffsets(singleton(tp));
        return Optional.ofNullable(endOffsets.get(tp));
    }

    private Consumer<String, Integer> createConsumer() {
        Deserializer<String> keyDeserializer = new StringDeserializer();
        Deserializer<Integer> valueDeserializer = new IntegerDeserializer();
        List<String> configsToRemove = emptyList();
        var configOverrides = new Properties();
        configOverrides.put(ConsumerConfig.GROUP_ID_CONFIG, "group1");
        configOverrides.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        return createConsumer(keyDeserializer, valueDeserializer, configOverrides, asScala(configsToRemove).toList());
    }

    @Override
    public int brokerCount() {
        return 1;
    }
}