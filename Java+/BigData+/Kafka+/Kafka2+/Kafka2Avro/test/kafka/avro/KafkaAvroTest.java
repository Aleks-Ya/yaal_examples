package kafka.avro;

import example.ActiveAppEvent;
import io.confluent.kafka.schemaregistry.client.MockSchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.rest.exceptions.RestClientException;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import kafka.api.IntegrationTestHarness;
import org.apache.avro.Schema;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.Serializer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import scala.collection.immutable.List;
import scala.jdk.javaapi.CollectionConverters;

import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Using {@link KafkaAvroSerializer} and {@link KafkaAvroDeserializer}.
 */
public class KafkaAvroTest extends IntegrationTestHarness {
    private static final String topic = KafkaAvroTest.class.getSimpleName().toLowerCase();

    @Test
    @Timeout(10_000)
    public void kafka() throws ExecutionException, InterruptedException, IOException, RestClientException {
        ActiveAppEvent value = new ActiveAppEvent("firefox", 1, 2, "no_errors");

        createTopic(topic, 1, 1, new Properties());

        Serializer<Long> keySerializer = new LongSerializer();
        SchemaRegistryClient client = new MockSchemaRegistryClient();
        Schema schema = value.getSchema();
        client.register("kafkaavrotest-value", schema);

        Serializer<Object> valueSerializer = new KafkaAvroSerializer(client);
        Properties producerConfig = new Properties();

        KafkaProducer<Long, Object> producer = createProducer(keySerializer, valueSerializer, producerConfig);
        ProducerRecord<Long, Object> record = new ProducerRecord<>(topic, 1L, value);
        producer.send(record).get();
        producer.close();

        Deserializer<Long> keyDeserializer = new LongDeserializer();
        Deserializer<Object> valueDeserializer = new KafkaAvroDeserializer(client);
        List<String> configsToRemove = CollectionConverters.asScala(Collections.<String>emptyList()).toList();
        KafkaConsumer<Long, Object> consumer = createConsumer(keyDeserializer, valueDeserializer, new Properties(), configsToRemove);
        consumer.subscribe(Collections.singleton(topic));
        ConsumerRecords<Long, Object> consumerRecords = consumer.poll(Duration.ofSeconds(1));
        consumer.close();

        assertThat(consumerRecords.count(), equalTo(1));
        assertThat(consumerRecords.iterator().next().value().toString(), equalTo(value.toString()));
    }

    @Override
    public int brokerCount() {
        return 1;
    }
}