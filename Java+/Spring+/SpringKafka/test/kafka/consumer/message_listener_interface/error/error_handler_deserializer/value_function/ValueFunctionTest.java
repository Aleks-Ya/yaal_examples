package kafka.consumer.message_listener_interface.error.error_handler_deserializer.value_function;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
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

/**
 * Use ErrorHandlingDeserializer.VALUE_FUNCTION property to replace a bad Kafka record.
 * Docs: https://docs.spring.io/spring-kafka/docs/current/reference/html/#error-handling-deserializer
 */
@ExtendWith(SpringExtension.class)
@EmbeddedKafka
@ContextConfiguration(classes = {ValueFunctionTest.class, KafkaMessageListener.class,
        KafkaConsumerConfig.class})
@TestPropertySource(properties = "topic=topic1")
class ValueFunctionTest {

    @Autowired
    private KafkaMessageListener consumer;
    @Value("${topic}")
    private String topic;
    @Autowired
    private EmbeddedKafkaBroker broker;

    @Test
    void badJson() {
        var value1Good = """
                {"id": 1, "name": "John"}
                """;
        var value2Bad = """
                { broken JSON }
                """;
        var value3Good = """
                {"id": 3, "name": "Mary"}
                """;
        var pf = new DefaultKafkaProducerFactory<Integer, String>(KafkaTestUtils.producerProps(broker));
        var template = new KafkaTemplate<>(pf);
        template.send(topic, value1Good);
        template.send(topic, value2Bad);
        template.send(topic, value3Good);

        await().timeout(15, TimeUnit.SECONDS).untilAsserted(() -> assertThat(consumer.getPersons())
                .containsExactlyInAnyOrder(new Person(1L, "John"),
                        new Person(0L, "Dow"), new Person(3L, "Mary")));
    }

    @Bean
    ConsumerProperties consumerPropertiesTest() {
        return new ConsumerProperties(Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, broker.getBrokersAsString(),
                ConsumerConfig.GROUP_ID_CONFIG, "groupTest",
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest",
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class,
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class,
                ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class,
                ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class,
                ErrorHandlingDeserializer.VALUE_FUNCTION, KafkaConsumerConfig.FailedProvider.class
        ));
    }
}

