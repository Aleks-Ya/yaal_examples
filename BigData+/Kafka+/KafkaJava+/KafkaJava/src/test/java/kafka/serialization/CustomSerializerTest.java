package kafka.serialization;

import kafka.api.IntegrationTestHarness;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Test;
import scala.collection.immutable.List;
import scala.jdk.javaapi.CollectionConverters;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThrows;

/**
 * Create a custom Serializer/Deserializer.
 */
public class CustomSerializerTest extends IntegrationTestHarness {
    private static final String topic = CustomSerializerTest.class.getSimpleName().toLowerCase();

    private static class Person implements Serializable {
        private final String name;
        private final Integer age;

        private Person(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Person person = (Person) o;
            return Objects.equals(name, person.name) &&
                    Objects.equals(age, person.age);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, age);
        }
    }

    public static class PersonSerDe implements Serializer<Person>, Deserializer<Person> {
        public static final String ALLOW_EMPTY_AGE_PROPERTY = "allow.empty.age";
        private Boolean allowEmptyAge = true;

        @Override
        public Person deserialize(String topic, byte[] data) {
            if (data == null) {
                return null;
            }
            try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data))) {
                Person person = (Person) ois.readObject();
                if (!allowEmptyAge && person.age == null) {
                    throw new IllegalArgumentException("Age is null");
                }
                return person;
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void configure(Map<String, ?> configs, boolean isKey) {
            Boolean allowEmptyAge = (Boolean) configs.get(ALLOW_EMPTY_AGE_PROPERTY);
            this.allowEmptyAge = allowEmptyAge != null ? allowEmptyAge : true;
        }

        @Override
        public byte[] serialize(String topic, Person data) {
            if (data == null) {
                return null;
            }
            if (!allowEmptyAge && data.age == null) {
                throw new IllegalArgumentException("Age is null");
            }
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
                 ObjectOutputStream oos = new ObjectOutputStream(baos)) {
                oos.writeObject(data);
                return baos.toByteArray();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void close() {
            // nothing
        }
    }

    @Test(timeout = 10_000)
    public void customNoException() throws ExecutionException, InterruptedException {
        Person value = new Person("John", 30);
        createTopic(topic, 1, 1, new Properties());
        producePerson(value);
        ConsumerRecords<String, Person> consumerRecords = consumePerson();
        assertThat(consumerRecords.count(), equalTo(1));
        assertThat(consumerRecords.iterator().next().value(), equalTo(value));
    }

    @Test(timeout = 10_000)
    public void customException() {
        Person value = new Person("John", null);
        createTopic(topic, 1, 1, new Properties());
        assertThrows("Age is null", IllegalArgumentException.class, () -> producePerson(value));
    }

    private ConsumerRecords<String, Person> consumePerson() {
        List<String> configsToRemove = CollectionConverters.asScala(Collections.<String>emptyList()).toList();
        Properties consumerConfig = new Properties();
        consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, PersonSerDe.class.getName());
        consumerConfig.put(PersonSerDe.ALLOW_EMPTY_AGE_PROPERTY, false);
        try (Consumer<String, Person> consumer = createConsumer(null, null,
                consumerConfig, configsToRemove)) {
            consumer.subscribe(Collections.singleton(topic));
            return consumer.poll(Duration.ofSeconds(1));
        }
    }

    private void producePerson(Person value) throws InterruptedException, ExecutionException {
        Properties producerConfig = new Properties();
        producerConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, PersonSerDe.class.getName());
        producerConfig.put(PersonSerDe.ALLOW_EMPTY_AGE_PROPERTY, false);
        try (Producer<String, Person> producer = createProducer(null, null, producerConfig)) {
            ProducerRecord<String, Person> record = new ProducerRecord<>(topic, value);
            producer.send(record).get();
        }
    }

    @Override
    public int brokerCount() {
        return 1;
    }
}