package kafka.compression;

import kafka.api.IntegrationTestHarness;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.Deserializer;
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
 * Message compression by GZip.
 */
public class CompressionTest extends IntegrationTestHarness {
    private static final String topic = CompressionTest.class.getSimpleName().toLowerCase();

    @Test(timeout = 10_000)
    public void compression() throws ExecutionException, InterruptedException {
        String value = "my message";

        createTopic(topic, 1, 1, new Properties());

        Properties producerConfigOverrides = new Properties();
        producerConfigOverrides.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "gzip");

        StringSerializer keySer = new StringSerializer();
        StringSerializer valueSer = new StringSerializer();
        try (Producer<String, String> producer = createProducer(keySer, valueSer, producerConfigOverrides)) {
            producer.send(new ProducerRecord<>(CompressionTest.topic, value)).get();
        }

        Deserializer<String> keyDes = new StringDeserializer();
        Deserializer<String> valueDes = new StringDeserializer();
        List<String> configsToRemove = CollectionConverters.asScala(Collections.<String>emptyList()).toList();
        try (Consumer<String, String> consumer = createConsumer(keyDes, valueDes, new Properties(), configsToRemove)) {
            consumer.subscribe(Collections.singleton(CompressionTest.topic));
            ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofSeconds(1));
            assertThat(consumerRecords.count(), equalTo(1));
            assertThat(consumerRecords.iterator().next().value(), equalTo(value));
        }

    }

    @Override
    public int brokerCount() {
        return 1;
    }
}