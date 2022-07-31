package kafka.consumer.kafka_listener_annotation.error.kafka_listener_error_handler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@ExtendWith(SpringExtension.class)
@EmbeddedKafka
@ContextConfiguration(classes = {KafkaListenerConsumer.class, KafkaConsumerConfig.class})
@TestPropertySource(properties = {"topic=topic1", "kafka.bootstrapAddress=${spring.embedded.kafka.brokers}"})
class KafkaListenerErrorHandlerTest {

    @Autowired
    private KafkaListenerConsumer consumer;
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
                {"id": 3, "name": "Ann"}
                """;

        var producerProps = KafkaTestUtils.producerProps(broker);
        var pf = new DefaultKafkaProducerFactory<Integer, String>(producerProps);
        var template = new KafkaTemplate<>(pf);
        template.send(topic, value1);
        template.send(topic, value2);
        template.send(topic, value3);

        await().timeout(15, TimeUnit.SECONDS)
                .untilAsserted(() -> {
                    assertThat(consumer.getPersons()).containsExactlyInAnyOrder(
                            new Person(1L, "John"), new Person(3L, "Ann"));
                    assertThat(consumer.getSkippedPersons()).containsExactlyInAnyOrder(new Person(2L, "Mary"));
                });
    }
}

