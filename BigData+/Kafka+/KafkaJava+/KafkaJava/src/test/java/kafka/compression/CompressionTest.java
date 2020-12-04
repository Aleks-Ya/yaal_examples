package kafka.compression;

import kafka.api.IntegrationTestHarness;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.config.TopicConfig;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Test;
import scala.collection.immutable.List;
import scala.jdk.javaapi.CollectionConverters;

import java.time.Duration;
import java.util.Collections;
import java.util.Iterator;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

/**
 * Message compression by GZip.
 */
public class CompressionTest extends IntegrationTestHarness {
    private static final String TOPIC = CompressionTest.class.getSimpleName().toLowerCase();
    private Integer serializedValueSize;

    @Test(timeout = 10_000)
    public void compression() throws ExecutionException, InterruptedException {
        String value = "message ".repeat(100000);

        var topicConfig = new Properties();
        var maxMessageBytes = value.length() / 2; //uncompressed value will not fit the max.message.bytes
        topicConfig.put(TopicConfig.MAX_MESSAGE_BYTES_CONFIG, String.valueOf(maxMessageBytes));
        createTopic(TOPIC, 1, 1, topicConfig);

        Properties producerConfigOverrides = new Properties();
        producerConfigOverrides.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "gzip");

        StringSerializer keySer = new StringSerializer();
        StringSerializer valueSer = new StringSerializer();
        try (Producer<String, String> producer = createProducer(keySer, valueSer, producerConfigOverrides)) {
            producer.send(new ProducerRecord<>(TOPIC, value),
                    (metadata, exception) -> serializedValueSize = metadata.serializedValueSize()
            ).get();
        }
        assertThat(serializedValueSize, greaterThan(maxMessageBytes));

        Deserializer<String> keyDes = new StringDeserializer();
        Deserializer<String> valueDes = new StringDeserializer();
        List<String> configsToRemove = CollectionConverters.asScala(Collections.<String>emptyList()).toList();
        try (Consumer<String, String> consumer = createConsumer(keyDes, valueDes, new Properties(), configsToRemove)) {
            consumer.subscribe(Collections.singleton(TOPIC));
            ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofSeconds(1));
            assertThat(consumerRecords.count(), equalTo(1));
            assertThat(consumerRecords.iterator().next().value(), equalTo(value));
        }
    }

    @Test(timeout = 10_000)
    public void mixCompressedAndUncompressedMessages() throws ExecutionException, InterruptedException {
        String compressedValue = "compressed message";
        String unCompressedValue = "uncompressed message";

        createTopic(TOPIC, 1, 1, new Properties());

        StringSerializer keySer = new StringSerializer();
        StringSerializer valueSer = new StringSerializer();

        Properties producerConfigOverrides = new Properties();
        producerConfigOverrides.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "gzip");
        try (Producer<String, String> compressedProducer = createProducer(keySer, valueSer, producerConfigOverrides)) {
            compressedProducer.send(new ProducerRecord<>(TOPIC, compressedValue)).get();
        }
        try (Producer<String, String> unCompressedProducer = createProducer(keySer, valueSer, new Properties())) {
            unCompressedProducer.send(new ProducerRecord<>(TOPIC, unCompressedValue)).get();
        }

        Deserializer<String> keyDes = new StringDeserializer();
        Deserializer<String> valueDes = new StringDeserializer();
        List<String> configsToRemove = CollectionConverters.asScala(Collections.<String>emptyList()).toList();
        try (Consumer<String, String> consumer = createConsumer(keyDes, valueDes, new Properties(), configsToRemove)) {
            consumer.subscribe(Collections.singleton(TOPIC));
            ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofSeconds(1));
            assertThat(consumerRecords.count(), equalTo(2));
            Iterator<ConsumerRecord<String, String>> iterator = consumerRecords.iterator();
            assertThat(iterator.next().value(), equalTo(compressedValue));
            assertThat(iterator.next().value(), equalTo(unCompressedValue));
        }
    }

    @Override
    public int brokerCount() {
        return 1;
    }
}