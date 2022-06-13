package kafka.header;

import kafka.BaseTest;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import util.CollectionUtil;

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
class MessageHeaderTest extends BaseTest {
    @Test
    @Timeout(10)
    void consume() throws ExecutionException, InterruptedException {
        var topic = "topic";
        createTopic(topic, 1, 1, new Properties());

        var key = "number";
        var value = 42;
        var headerKey = "importance";
        var headerValue = "very high".getBytes();

        produce(topic, key, value, headerKey, headerValue);

        var records = consumeRecords(topic);

        var record0 = records.get(0);
        var headers = record0.headers();
        var header = headers.lastHeader(headerKey);
        var headerValueAct = header.value();
        assertThat(headerValueAct, equalTo(headerValue));

    }

    private void produce(String topic, String key, Integer value, String headerKey, byte[] headerValue)
            throws InterruptedException, ExecutionException {
        Serializer<String> keySerializer = new StringSerializer();
        Serializer<Integer> valueSerializer = new IntegerSerializer();
        try (Producer<String, Integer> producer = createProducer(keySerializer, valueSerializer, new Properties())) {
            Header header = new RecordHeader(headerKey, headerValue);
            Collection<Header> headers = singleton(header);
            var record = new ProducerRecord<>(topic, 0, key, value, headers);
            producer.send(record).get();
        }
    }

    private List<ConsumerRecord<String, Integer>> consumeRecords(String topic) {
        try (var consumer = createConsumer()) {
            consumer.subscribe(singleton(topic));
            var records = consumer.poll(Duration.ofSeconds(1));
            var topicRecords = records.records(topic);
            return CollectionUtil.iterableToList(topicRecords);
        }
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