package kafka.serialization;

import kafka.api.IntegrationTestHarness;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.common.serialization.UUIDDeserializer;
import org.apache.kafka.common.serialization.UUIDSerializer;
import org.junit.Test;
import scala.collection.immutable.List;
import scala.jdk.javaapi.CollectionConverters;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Using {@link org.apache.kafka.common.serialization.UUIDSerializer}.
 */
public class UuidSerializerTest extends IntegrationTestHarness {
    private static final String topic = UuidSerializerTest.class.getSimpleName().toLowerCase();

    @Test(timeout = 10_000)
    public void uuidSerializer() throws ExecutionException, InterruptedException {
        UUID value = UUID.fromString("f4c7c119-a3b0-4df4-b971-e627d00bafb4");

        createTopic(topic, 1, 1, new Properties());

        Serializer<String> keySerializer = new StringSerializer();
        Serializer<UUID> valueSerializer = new UUIDSerializer();
        KafkaProducer<String, UUID> producer = createProducer(keySerializer, valueSerializer, new Properties());
        ProducerRecord<String, UUID> record = new ProducerRecord<>(topic, value);
        producer.send(record).get();
        producer.close();

        Deserializer<String> keyDeserializer = new StringDeserializer();
        Deserializer<UUID> valueDeserializer = new UUIDDeserializer();
        List<String> configsToRemove = CollectionConverters.asScala(Collections.<String>emptyList()).toList();
        KafkaConsumer<String, UUID> consumer = createConsumer(keyDeserializer, valueDeserializer, new Properties(), configsToRemove);
        consumer.subscribe(Collections.singleton(topic));
        ConsumerRecords<String, UUID> consumerRecords = consumer.poll(Duration.ofSeconds(1));
        consumer.close();

        assertThat(consumerRecords.count(), equalTo(1));
        assertThat(consumerRecords.iterator().next().value(), equalTo(value));
    }

    @Override
    public int brokerCount() {
        return 1;
    }
}