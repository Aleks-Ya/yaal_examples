package kafka.test_harness;

import kafka.api.IntegrationTestHarness;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import scala.collection.immutable.List;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Instantiate Consumer and Producer
 * using {@link IntegrationTestHarness#consumerConfig()} and {@link IntegrationTestHarness#producerConfig()}
 * instead of {@link IntegrationTestHarness#createProducer(Serializer, Serializer, Properties)}
 * and {@link IntegrationTestHarness#createConsumer(Deserializer, Deserializer, Properties, List)}.
 */
class InstantiateClientFromConfigTest extends IntegrationTestHarness {

    @Test
    @Timeout(10)
    void test() throws ExecutionException, InterruptedException {
        var topic = "the_topic";
        createTopic(topic, 1, 1, new Properties());

        var value = "abc";

        Serializer<Integer> keySer = new IntegerSerializer();
        Serializer<String> valueSer = new StringSerializer();
        try (Producer<Integer, String> producer = new KafkaProducer<>(producerConfig(), keySer, valueSer)) {
            var record = new ProducerRecord<Integer, String>(topic, value);
            producer.send(record).get();
        }

        Deserializer<Integer> keyDes = new IntegerDeserializer();
        Deserializer<String> valueDes = new StringDeserializer();
        try (Consumer<Integer, String> consumer = new KafkaConsumer<>(consumerConfig(), keyDes, valueDes)) {
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