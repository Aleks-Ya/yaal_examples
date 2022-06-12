package kafka.binary;

import kafka.api.IntegrationTestHarness;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import scala.jdk.javaapi.CollectionConverters;
import util.CollectionUtil;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Transfer binary messages.
 */
class BinaryMessageTest extends IntegrationTestHarness {

    @Test
    @Timeout(10)
    void consume() throws ExecutionException, InterruptedException {
        var topic = "topic";
        createTopic(topic, 1, 1, new Properties());

        var expBytes = "abc".getBytes();
        produce(topic, expBytes);

        try (var consumer = createConsumer()) {
            var recordList = consumeRecords(topic, consumer);
            var record1 = recordList.get(0);
            var actBytes = record1.value();
            assertThat(actBytes, equalTo(expBytes));
        }
    }

    private static java.util.List<ConsumerRecord<String, byte[]>> consumeRecords(String topic,
                                                                                 Consumer<String, byte[]> consumer) {
        consumer.subscribe(Collections.singleton(topic));
        var records = consumer.poll(Duration.ofSeconds(1));
        var topicRecords = records.records(topic);
        return CollectionUtil.iterableToList(topicRecords);
    }

    private void produce(String topic, byte[] bytes) throws InterruptedException, ExecutionException {
        Serializer<String> keySerializer = new StringSerializer();
        Serializer<byte[]> valueSerializer = new ByteArraySerializer();
        try (Producer<String, byte[]> producer = createProducer(keySerializer, valueSerializer, new Properties())) {
            var record = new ProducerRecord<String, byte[]>(topic, bytes);
            producer.send(record).get();
        }
    }

    private Consumer<String, byte[]> createConsumer() {
        Deserializer<String> keyDeserializer = new StringDeserializer();
        Deserializer<byte[]> valueDeserializer = new ByteArrayDeserializer();
        var configsToRemove = CollectionConverters.asScala(Collections.<String>emptyList()).toList();
        var configOverrides = new Properties();
        configOverrides.put(ConsumerConfig.GROUP_ID_CONFIG, "group1");
        return createConsumer(keyDeserializer, valueDeserializer, configOverrides, configsToRemove);
    }

    @Override
    public int brokerCount() {
        return 1;
    }
}