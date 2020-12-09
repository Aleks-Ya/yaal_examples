package kafka;

import kafka.api.IntegrationTestHarness;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.Collections.singleton;
import static scala.jdk.javaapi.CollectionConverters.asScala;

/**
 * Base Kafka test with convenience methods.
 */
public abstract class BaseTest extends IntegrationTestHarness {
    private static final Random random = new Random();

    protected void produceIntegers(String topic, Integer... values) throws InterruptedException, ExecutionException {
        Serializer<String> keySerializer = new StringSerializer();
        Serializer<Integer> valueSerializer = new IntegerSerializer();
        try (Producer<String, Integer> producer = createProducer(keySerializer, valueSerializer, new Properties())) {
            for (Integer value : values) {
                ProducerRecord<String, Integer> record = new ProducerRecord<>(topic, value);
                producer.send(record).get();
            }
        }
    }

    protected void produceStrings(String topic, String... values) throws InterruptedException, ExecutionException {
        Serializer<String> keySerializer = new StringSerializer();
        Serializer<String> valueSerializer = new StringSerializer();
        try (Producer<String, String> producer = createProducer(keySerializer, valueSerializer, new Properties())) {
            for (String value : values) {
                ProducerRecord<String, String> record = new ProducerRecord<>(topic, value);
                producer.send(record).get();
            }
        }
    }

    protected String generateRandomGroupId() {
        return "group-" + random.nextInt(Integer.MAX_VALUE);
    }

    protected Consumer<String, String> createStringConsumer(Map<String, String> configOverride) {
        Deserializer<String> keyDeserializer = new StringDeserializer();
        Deserializer<String> valueDeserializer = new StringDeserializer();
        List<String> configsToRemove = emptyList();
        Properties configOverrides = new Properties();
        configOverrides.put(ConsumerConfig.GROUP_ID_CONFIG, generateRandomGroupId());
        configOverrides.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        if (configOverride != null) {
            configOverrides.putAll(configOverride);
        }
        return createConsumer(keyDeserializer, valueDeserializer, configOverrides, asScala(configsToRemove).toList());
    }

    protected List<String> consumeAllStringsFromBeginning(String topic) {
        var consumer = createStringConsumer(Map.of());
        consumer.subscribe(singleton(topic));
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
        consumer.commitSync();
        Iterable<ConsumerRecord<String, String>> topicRecords = records.records(topic);
        List<ConsumerRecord<String, String>> recordList = Utils.iterableToList(topicRecords);
        return recordList.stream().map(ConsumerRecord::value).collect(Collectors.toList());
    }

}