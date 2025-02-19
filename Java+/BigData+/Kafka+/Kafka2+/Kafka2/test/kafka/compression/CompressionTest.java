package kafka.compression;

import kafka.api.IntegrationTestHarness;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.config.TopicConfig;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import scala.jdk.javaapi.CollectionConverters;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Message compression by GZip.
 */
class CompressionTest extends IntegrationTestHarness {
    private static final String TOPIC = CompressionTest.class.getSimpleName().toLowerCase();
    private Integer serializedValueSize;

    @Test
    @Timeout(10)
    void compression() throws ExecutionException, InterruptedException {
        var value = "message ".repeat(100000);

        var topicConfig = new Properties();
        var maxMessageBytes = value.length() / 2; //uncompressed value will not fit the max.message.bytes
        topicConfig.put(TopicConfig.MAX_MESSAGE_BYTES_CONFIG, String.valueOf(maxMessageBytes));
        createTopic(TOPIC, 1, 1, topicConfig);

        var producerConfigOverrides = new Properties();
        producerConfigOverrides.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "gzip");

        var keySer = new StringSerializer();
        var valueSer = new StringSerializer();
        try (Producer<String, String> producer = createProducer(keySer, valueSer, producerConfigOverrides)) {
            producer.send(new ProducerRecord<>(TOPIC, value),
                    (metadata, exception) -> serializedValueSize = metadata.serializedValueSize()
            ).get();
        }
        assertThat(serializedValueSize).isGreaterThan(maxMessageBytes);

        Deserializer<String> keyDes = new StringDeserializer();
        Deserializer<String> valueDes = new StringDeserializer();
        var configsToRemove = CollectionConverters.asScala(Collections.<String>emptyList()).toList();
        try (Consumer<String, String> consumer = createConsumer(keyDes, valueDes, new Properties(), configsToRemove)) {
            consumer.subscribe(Collections.singleton(TOPIC));
            var consumerRecords = consumer.poll(Duration.ofSeconds(1));
            assertThat(consumerRecords.count()).isEqualTo(1);
            assertThat(consumerRecords.iterator().next().value()).isEqualTo(value);
        }
    }

    @Test
    @Timeout(10)
    void mixCompressedAndUncompressedMessages() throws ExecutionException, InterruptedException {
        var compressedValue = "compressed message";
        var unCompressedValue = "uncompressed message";

        createTopic(TOPIC, 1, 1, new Properties());

        var keySer = new StringSerializer();
        var valueSer = new StringSerializer();

        var producerConfigOverrides = new Properties();
        producerConfigOverrides.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "gzip");
        try (Producer<String, String> compressedProducer = createProducer(keySer, valueSer, producerConfigOverrides)) {
            compressedProducer.send(new ProducerRecord<>(TOPIC, compressedValue)).get();
        }
        try (Producer<String, String> unCompressedProducer = createProducer(keySer, valueSer, new Properties())) {
            unCompressedProducer.send(new ProducerRecord<>(TOPIC, unCompressedValue)).get();
        }

        Deserializer<String> keyDes = new StringDeserializer();
        Deserializer<String> valueDes = new StringDeserializer();
        var configsToRemove = CollectionConverters.asScala(Collections.<String>emptyList()).toList();
        try (Consumer<String, String> consumer = createConsumer(keyDes, valueDes, new Properties(), configsToRemove)) {
            consumer.subscribe(Collections.singleton(TOPIC));
            var consumerRecords = consumer.poll(Duration.ofSeconds(1));
            assertThat(consumerRecords.count()).isEqualTo(2);
            var iterator = consumerRecords.iterator();
            assertThat(iterator.next().value()).isEqualTo(compressedValue);
            assertThat(iterator.next().value()).isEqualTo(unCompressedValue);
        }
    }

    @Override
    public int brokerCount() {
        return 1;
    }
}