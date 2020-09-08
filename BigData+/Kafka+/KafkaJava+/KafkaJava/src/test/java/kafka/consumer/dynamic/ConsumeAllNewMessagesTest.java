package kafka.consumer.dynamic;

import kafka.BaseTest;
import kafka.Utils;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.Test;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static com.spotify.hamcrest.optional.OptionalMatchers.optionalWithValue;
import static java.util.Collections.emptyList;
import static java.util.Collections.singleton;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static scala.jdk.javaapi.CollectionConverters.asScala;

/**
 * Consumer all new messages added to a topic after the last run.
 * (dynamic partition assignment with {@link KafkaConsumer#subscribe})
 */
public class ConsumeAllNewMessagesTest extends BaseTest {

    @Test(timeout = 10_000)
    public void consume() throws ExecutionException, InterruptedException {
        String topic = "topic";
        createTopic(topic, 1, 1, new Properties());

        //Consume first messages (#1 and #2)
        int expValue1 = 42;
        int expValue2 = 43;
        produceIntegers(topic, expValue1, expValue2);
        try (Consumer<String, Integer> consumer = createConsumer()) {
            assertThat(consumeRecords(consumer, topic), contains(expValue1, expValue2));
            assertThat(getCommittedOffset(consumer, topic), optionalWithValue(equalTo(2L)));
            assertThat(getEndOffset(consumer, topic), optionalWithValue(equalTo(2L)));
        }

        //Consume new messages (#3 and #4)
        int expValue3 = 44;
        int expValue4 = 45;
        produceIntegers(topic, expValue3, expValue4);
        try (Consumer<String, Integer> consumer = createConsumer()) {
            assertThat(consumeRecords(consumer, topic), contains(expValue3, expValue4));
            assertThat(getCommittedOffset(consumer, topic), optionalWithValue(equalTo(4L)));
            assertThat(getEndOffset(consumer, topic), optionalWithValue(equalTo(4L)));
        }

        //Consume no new messages
        try (Consumer<String, Integer> consumer = createConsumer()) {
            assertThat(consumeRecords(consumer, topic), empty());
            assertThat(getCommittedOffset(consumer, topic), optionalWithValue(equalTo(4L)));
            assertThat(getEndOffset(consumer, topic), optionalWithValue(equalTo(4L)));
        }

        //Consume new messages added between polls (#5, #6, #7, #8)
        int expValue5 = 46;
        int expValue6 = 47;
        produceIntegers(topic, expValue5, expValue6);
        try (Consumer<String, Integer> consumer = createConsumer()) {
            assertThat(consumeRecords(consumer, topic), contains(expValue5, expValue6));
            assertThat(getCommittedOffset(consumer, topic), optionalWithValue(equalTo(6L)));
            assertThat(getEndOffset(consumer, topic), optionalWithValue(equalTo(6L)));

            int expValue7 = 48;
            int expValue8 = 49;
            produceIntegers(topic, expValue7, expValue8);
            assertThat(getCommittedOffset(consumer, topic), optionalWithValue(equalTo(6L)));
            assertThat(getEndOffset(consumer, topic), optionalWithValue(equalTo(8L)));
            assertThat(consumeRecords(consumer, topic), contains(expValue7, expValue8));
            assertThat(getCommittedOffset(consumer, topic), optionalWithValue(equalTo(8L)));
            assertThat(getEndOffset(consumer, topic), optionalWithValue(equalTo(8L)));
        }
    }

    private List<Integer> consumeRecords(Consumer<String, Integer> consumer, String topic) {
        consumer.subscribe(singleton(topic));
        ConsumerRecords<String, Integer> records = consumer.poll(Duration.ofSeconds(1));
        consumer.commitSync();
        Iterable<ConsumerRecord<String, Integer>> topicRecords = records.records(topic);
        List<ConsumerRecord<String, Integer>> recordList = Utils.iterableToList(topicRecords);
        return recordList.stream().map(ConsumerRecord::value).collect(Collectors.toList());
    }

    private Optional<Long> getCommittedOffset(Consumer<String, Integer> consumer, String topic) {
        consumer.subscribe(singleton(topic));
        TopicPartition tp = new TopicPartition(topic, 0);
        Map<TopicPartition, OffsetAndMetadata> committed = consumer.committed(singleton(tp));
        if (committed.containsKey(tp)) {
            return Optional.of(committed.get(tp).offset());
        } else {
            return Optional.empty();
        }
    }

    private Optional<Long> getEndOffset(Consumer<String, Integer> consumer, String topic) {
        consumer.subscribe(singleton(topic));
        TopicPartition tp = new TopicPartition(topic, 0);
        Map<TopicPartition, Long> endOffsets = consumer.endOffsets(singleton(tp));
        return Optional.ofNullable(endOffsets.get(tp));
    }

    private Consumer<String, Integer> createConsumer() {
        Deserializer<String> keyDeserializer = new StringDeserializer();
        Deserializer<Integer> valueDeserializer = new IntegerDeserializer();
        List<String> configsToRemove = emptyList();
        Properties configOverrides = new Properties();
        configOverrides.put(ConsumerConfig.GROUP_ID_CONFIG, "group1");
        configOverrides.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        return createConsumer(keyDeserializer, valueDeserializer, configOverrides, asScala(configsToRemove).toList());
    }

    @Override
    public int brokerCount() {
        return 1;
    }
}