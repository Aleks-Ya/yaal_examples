package kafka.replication;

import kafka.api.IntegrationTestHarness;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
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
import org.junit.Ignore;
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
 * Create a replicated topic.
 */
public class ReplicatedTopicTest extends IntegrationTestHarness {
    private static final int REPLICATION_FACTOR = 2;

    @Test(timeout = 30_000)
    @Ignore
    public void commitOffset() throws ExecutionException, InterruptedException {
        String topic = ReplicatedTopicTest.class.getSimpleName().toLowerCase();
        Integer value1 = 42;
        Integer value2 = 43;
        Integer value3 = 44;

        createTopic(topic, 1, REPLICATION_FACTOR, new Properties());

        Serializer<String> keySer = new StringSerializer();
        Serializer<Integer> valueSer = new IntegerSerializer();

        Deserializer<String> keyDes = new StringDeserializer();
        Deserializer<Integer> valueDe = new IntegerDeserializer();
        List<String> configsToRemove = CollectionConverters.asScala(Collections.<String>emptyList()).toList();
        Properties configOverrides = new Properties();

        try (Producer<String, Integer> producer = createProducer(keySer, valueSer, new Properties());
             Consumer<String, Integer> consumer = createConsumer(keyDes, valueDe, configOverrides, configsToRemove)) {

            consumer.subscribe(Collections.singleton(topic));

            producer.send(new ProducerRecord<>(topic, value1)).get();
            ConsumerRecords<String, Integer> records1 = consumer.poll(Duration.ofSeconds(1));
            assertThat(records1.count(), equalTo(1));
            assertThat(records1.iterator().next().value(), equalTo(value1));
//        killRandomBroker();
            killBroker(0);


            producer.send(new ProducerRecord<>(topic, value2)).get();
            ConsumerRecords<String, Integer> records2 = consumer.poll(Duration.ofSeconds(1));
            assertThat(records2.count(), equalTo(1));
            assertThat(records2.iterator().next().value(), equalTo(value2));
//        killRandomBroker();
            killBroker(1);

            producer.send(new ProducerRecord<>(topic, value3)).get();
            ConsumerRecords<String, Integer> records3 = consumer.poll(Duration.ofSeconds(1));

            // Consume the record from any of 2 brokers
//        ConsumerRecords<String, Integer> records1 = consumeByNewConsumerFromAnotherGroup(topic);
//        assertThat(records1.count(), equalTo(1));
//        assertThat(records1.iterator().next().value(), equalTo(value));

            // Kill one broker
//        killRandomBroker();

            // Consume the record from the last broker
//        ConsumerRecords<String, Integer> records2 = consumeByNewConsumerFromAnotherGroup(topic);
//        assertThat(records2.count(), equalTo(1));
//        assertThat(records2.iterator().next().value(), equalTo(value1));

            // Kill the last broker
//        killRandomBroker();

            // Cannot consume record, because no brokers are available
//        ConsumerRecords<String, Integer> records3 = consumeByNewConsumerFromAnotherGroup(topic);
//        assertThat(records3.isEmpty(), is(true));
        }
    }

    private void produce(String topic, Integer value) throws InterruptedException, ExecutionException {
        Serializer<String> keySerializer = new StringSerializer();
        Serializer<Integer> valueSerializer = new IntegerSerializer();
        KafkaProducer<String, Integer> producer = createProducer(keySerializer, valueSerializer, new Properties());
        ProducerRecord<String, Integer> record = new ProducerRecord<>(topic, value);
        producer.send(record).get();
        producer.close();
    }

    private ConsumerRecords<String, Integer> consumeByNewConsumerFromAnotherGroup(String topic) {
        Deserializer<String> keyDeserializer = new StringDeserializer();
        Deserializer<Integer> valueDeserializer = new IntegerDeserializer();
        List<String> configsToRemove = CollectionConverters.asScala(Collections.<String>emptyList()).toList();
        Properties configOverrides = new Properties();
//        configOverrides.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
//        configOverrides.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());
        KafkaConsumer<String, Integer> consumer = createConsumer(keyDeserializer, valueDeserializer, configOverrides, configsToRemove);
        consumer.subscribe(Collections.singleton(topic));
        ConsumerRecords<String, Integer> records = consumer.poll(Duration.ofSeconds(1));
//        if (commit) {
//            consumer.commitSync();
//        }
        consumer.close();
        return records;
    }

    @Override
    public int brokerCount() {
        return REPLICATION_FACTOR + 1;
    }
}