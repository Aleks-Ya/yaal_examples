package kafka.embedded_kafka;

import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.allOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.kafka.test.assertj.KafkaConditions.value;

/**
 * Produce messages to a topic for testing purposes.
 */
@EmbeddedKafka
class ProduceTestingMessagesTest {
    @Test
    void produce(EmbeddedKafkaBroker broker) {
        var topic = "templateTopic";
        var value = "foo";

        var producerProps = KafkaTestUtils.producerProps(broker);
        ProducerFactory<Integer, String> pf = new DefaultKafkaProducerFactory<>(producerProps);
        var template = new KafkaTemplate<>(pf);
        template.setDefaultTopic(topic);
        template.sendDefault(value);

        var consumerProps = KafkaTestUtils.consumerProps("testT", "false", broker);
        var cf = new DefaultKafkaConsumerFactory<Integer, String>(consumerProps);
        var consumer = cf.createConsumer();
        consumer.subscribe(List.of(topic));
        var record = KafkaTestUtils.getSingleRecord(consumer, topic);
        assertThat(record).has(allOf(value(value)));
    }
}

