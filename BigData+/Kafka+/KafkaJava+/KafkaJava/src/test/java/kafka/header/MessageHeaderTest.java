package kafka.header;

import kafka.BaseTest;
import kafka.Utils;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Test;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import static java.util.Collections.emptyList;
import static java.util.Collections.singleton;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static scala.jdk.javaapi.CollectionConverters.asScala;

/**
 * Adding a custom message header.
 */
public class MessageHeaderTest extends BaseTest {
    @Test(timeout = 10_000)
    public void consume() throws ExecutionException, InterruptedException {
        String topic = "topic";
        createTopic(topic, 1, 1, new Properties());

        String key = "number";
        int value = 42;
        String headerKey = "importance";
        byte[] headerValue = "very high".getBytes();

        produce(topic, key, value, headerKey, headerValue);

        List<ConsumerRecord<String, Integer>> records = consumeRecords(topic);

        ConsumerRecord<String, Integer> record0 = records.get(0);
        Headers headers = record0.headers();
        Header header = headers.lastHeader(headerKey);
        byte[] headerValueAct = header.value();
        assertThat(headerValueAct, equalTo(headerValue));

    }

    private void produce(String topic, String key, Integer value, String headerKey, byte[] headerValue)
            throws InterruptedException, ExecutionException {
        Serializer<String> keySerializer = new StringSerializer();
        Serializer<Integer> valueSerializer = new IntegerSerializer();
        try (Producer<String, Integer> producer = createProducer(keySerializer, valueSerializer, new Properties())) {
            Header header = new RecordHeader(headerKey, headerValue);
            Collection<Header> headers = singleton(header);
            ProducerRecord<String, Integer> record = new ProducerRecord<>(topic, 0, key, value, headers);
            producer.send(record).get();
        }
    }

    private List<ConsumerRecord<String, Integer>> consumeRecords(String topic) {
        try (Consumer<String, Integer> consumer = createConsumer()) {
            consumer.subscribe(singleton(topic));
            ConsumerRecords<String, Integer> records = consumer.poll(Duration.ofSeconds(1));
            Iterable<ConsumerRecord<String, Integer>> topicRecords = records.records(topic);
            return Utils.iterableToList(topicRecords);
        }
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