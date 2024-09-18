package kafka.consumer;

import kafka.BaseTest;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import scala.jdk.javaapi.CollectionConverters;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Handle exception during deserialization.
 */
class DeserializationExceptionTest extends BaseTest {

    @Test
    @Timeout(10)
    void consume() {
        assertThatThrownBy(() -> {
            var topic = "topic";
            createTopic(topic, 1, 1, new Properties());

            produceStrings(topic, "abc");

            try (var consumer = createConsumer()) {
                consumer.subscribe(Collections.singleton(topic));
                consumer.poll(Duration.ofSeconds(1));
            }
        }).isInstanceOf(SerializationException.class);
    }

    private Consumer<String, Integer> createConsumer() {
        Deserializer<String> keyDeserializer = new StringDeserializer();
        Deserializer<Integer> valueDeserializer = new IntegerDeserializer();
        var configsToRemove = CollectionConverters.asScala(Collections.<String>emptyList()).toList();
        var configOverrides = new Properties();
        configOverrides.put(ConsumerConfig.GROUP_ID_CONFIG, "group1");
        configOverrides.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        return createConsumer(keyDeserializer, valueDeserializer, configOverrides, configsToRemove);
    }

    @Override
    public int brokerCount() {
        return 1;
    }
}