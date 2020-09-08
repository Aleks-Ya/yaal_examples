package kafka;

import kafka.api.IntegrationTestHarness;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * Base Kafka test with convenience methods.
 */
public abstract class BaseTest extends IntegrationTestHarness {

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

}