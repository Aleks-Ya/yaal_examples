package kafka.binary;

import kafka.Utils;
import kafka.api.IntegrationTestHarness;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.Deserializer;
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

/**
 * Transfer binary messages.
 */
public class BinaryMessageTest extends IntegrationTestHarness {

    @Test(timeout = 10_000)
    public void consume() throws ExecutionException, InterruptedException {
        String topic = "topic";
        createTopic(topic, 1, 1, new Properties());

        byte[] expBytes = "abc".getBytes();
        produce(topic, expBytes);

        try (Consumer<String, byte[]> consumer = createConsumer()) {
            java.util.List<ConsumerRecord<String, byte[]>> recordList = consumeRecords(topic, consumer);
            ConsumerRecord<String, byte[]> record1 = recordList.get(0);
            byte[] actBytes = record1.value();
            assertThat(actBytes, equalTo(expBytes));
        }
    }

    private static java.util.List<ConsumerRecord<String, byte[]>> consumeRecords(String topic,
                                                                                 Consumer<String, byte[]> consumer) {
        consumer.subscribe(Collections.singleton(topic));
        ConsumerRecords<String, byte[]> records = consumer.poll(Duration.ofSeconds(1));
        Iterable<ConsumerRecord<String, byte[]>> topicRecords = records.records(topic);
        return Utils.iterableToList(topicRecords);
    }

    private void produce(String topic, byte[] bytes) throws InterruptedException, ExecutionException {
        Serializer<String> keySerializer = new StringSerializer();
        Serializer<byte[]> valueSerializer = new ByteArraySerializer();
        try (Producer<String, byte[]> producer = createProducer(keySerializer, valueSerializer, new Properties())) {
            ProducerRecord<String, byte[]> record = new ProducerRecord<>(topic, bytes);
            producer.send(record).get();
        }
    }

    private Consumer<String, byte[]> createConsumer() {
        Deserializer<String> keyDeserializer = new StringDeserializer();
        Deserializer<byte[]> valueDeserializer = new ByteArrayDeserializer();
        List<String> configsToRemove = CollectionConverters.asScala(Collections.<String>emptyList()).toList();
        Properties configOverrides = new Properties();
        configOverrides.put(ConsumerConfig.GROUP_ID_CONFIG, "group1");
        return createConsumer(keyDeserializer, valueDeserializer, configOverrides, configsToRemove);
    }

    @Override
    public int brokerCount() {
        return 1;
    }
}