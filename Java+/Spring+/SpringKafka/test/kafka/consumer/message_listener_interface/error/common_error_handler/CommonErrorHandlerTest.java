package kafka.consumer.message_listener_interface.error.common_error_handler;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@ExtendWith(SpringExtension.class)
@EmbeddedKafka
@ContextConfiguration(classes = {KafkaMessageListener.class, KafkaConsumerConfig.class})
@TestPropertySource(properties = {"topic=topic1", "kafka.bootstrapAddress=${spring.embedded.kafka.brokers}"})
class CommonErrorHandlerTest {

    @Autowired
    private KafkaMessageListener consumer;
    @Value("${topic}")
    private String topic;
    @Autowired
    private EmbeddedKafkaBroker broker;

    @Test
    void test() {
        var value1 = """
                {"id": 1, "name": "John"}
                """;
        var value2 = """
                {"id": 2, "name": "Mary"}
                """;
        var value3 = """
                {"id": 3, "name": "Jack"}
                """;
        var pf = new DefaultKafkaProducerFactory<Integer, String>(KafkaTestUtils.producerProps(broker));
        var template = new KafkaTemplate<>(pf);
        template.send(topic, value1);
        template.send(topic, value2);
        template.send(topic, value3);

        await().timeout(15, TimeUnit.SECONDS).untilAsserted(() -> assertThat(consumer.getPersons()).contains(
                new Person(1L, "John"), new Person(3L, "Jack")));
    }
}

