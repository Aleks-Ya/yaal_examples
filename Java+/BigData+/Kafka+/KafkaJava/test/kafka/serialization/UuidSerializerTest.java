package kafka.serialization;

import kafka.api.IntegrationTestHarness;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.common.serialization.UUIDDeserializer;
import org.apache.kafka.common.serialization.UUIDSerializer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import scala.jdk.javaapi.CollectionConverters;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Using {@link UUIDSerializer}.
 */
class UuidSerializerTest extends IntegrationTestHarness {
    private static final String topic = UuidSerializerTest.class.getSimpleName().toLowerCase();

    @Test
    @Timeout(10)
    void uuidSerializer() throws ExecutionException, InterruptedException {
        var value = UUID.fromString("f4c7c119-a3b0-4df4-b971-e627d00bafb4");

        createTopic(topic, 1, 1, new Properties());

        Serializer<String> keySerializer = new StringSerializer();
        Serializer<UUID> valueSerializer = new UUIDSerializer();
        try (Producer<String, UUID> producer = createProducer(keySerializer, valueSerializer, new Properties())) {
            var record = new ProducerRecord<String, UUID>(topic, value);
            producer.send(record).get();
        }

        Deserializer<String> keyDeserializer = new StringDeserializer();
        Deserializer<UUID> valueDeserializer = new UUIDDeserializer();
        var configsToRemove = CollectionConverters.asScala(Collections.<String>emptyList()).toList();
        try (Consumer<String, UUID> consumer = createConsumer(keyDeserializer, valueDeserializer,
                new Properties(), configsToRemove)) {
            consumer.subscribe(Collections.singleton(topic));
            var consumerRecords = consumer.poll(Duration.ofSeconds(1));
            assertThat(consumerRecords.count(), equalTo(1));
            assertThat(consumerRecords.iterator().next().value(), equalTo(value));
        }

    }

    @Override
    public int brokerCount() {
        return 1;
    }
}