package kafka.embedded_kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
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
    void produceSingleRecord(EmbeddedKafkaBroker broker) {
        var topic = "produceSingleRecord";
        var value = "foo";

        var producerProps = KafkaTestUtils.producerProps(broker);
        var pf = new DefaultKafkaProducerFactory<Integer, String>(producerProps);
        var template = new KafkaTemplate<>(pf);
        template.setDefaultTopic(topic);
        template.sendDefault(value);

        var consumerProps = KafkaTestUtils.consumerProps("produceSingleRecord", "false", broker);
        var cf = new DefaultKafkaConsumerFactory<Integer, String>(consumerProps);
        var consumer = cf.createConsumer();
        consumer.subscribe(List.of(topic));
        var record = KafkaTestUtils.getSingleRecord(consumer, topic);
        assertThat(record).has(allOf(value(value)));
    }

    @Test
    void produceMultipleRecords(EmbeddedKafkaBroker broker) {
        var topic = "produceMultipleRecords";
        var value1 = "abc";
        var value2 = "xyz";

        var producerProps = KafkaTestUtils.producerProps(broker);
        var pf = new DefaultKafkaProducerFactory<Integer, String>(producerProps);
        var template = new KafkaTemplate<>(pf);
        template.setDefaultTopic(topic);
        template.sendDefault(value1);
        template.sendDefault(value2);

        var consumerProps = KafkaTestUtils.consumerProps("produceMultipleRecords", "false", broker);
        var cf = new DefaultKafkaConsumerFactory<Integer, String>(consumerProps);
        var consumer = cf.createConsumer();
        consumer.subscribe(List.of(topic));
        var records = KafkaTestUtils.getRecords(consumer).records(topic);
        assertThat(records).map(ConsumerRecord::value).contains(value1, value2);
    }
}

