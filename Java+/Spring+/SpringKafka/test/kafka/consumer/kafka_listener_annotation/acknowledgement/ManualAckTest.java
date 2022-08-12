package kafka.consumer.kafka_listener_annotation.acknowledgement;

import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
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

import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static kafka.consumer.kafka_listener_annotation.acknowledgement.KafkaConsumerConfig.GROUP_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@ExtendWith(SpringExtension.class)
@EmbeddedKafka
@ContextConfiguration(classes = {KafkaListenerConsumer.class, KafkaConsumerConfig.class})
@TestPropertySource(properties = {"topic=topic1", "kafka.bootstrapAddress=${spring.embedded.kafka.brokers}"})
class ManualAckTest {

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

        System.out.println("Offset 1: " + getConsumerGroupOffset());
        var pf = new DefaultKafkaProducerFactory<Integer, String>(KafkaTestUtils.producerProps(broker));
        var template = new KafkaTemplate<>(pf);
        template.send(topic, value1);
        template.send(topic, value2);

        System.out.println("Offset 2: " + getConsumerGroupOffset());

        await().timeout(15, TimeUnit.SECONDS)
                .untilAsserted(() -> assertThat(consumer.getPersons())
                        .contains(new Person(1L, "John"), new Person(2L, "Mary")));

        System.out.println("Offset 3: " + getConsumerGroupOffset());
    }

    private Optional<Long> getConsumerGroupOffset() {
        return broker.doWithAdminFunction(adminClient -> {
            try {
                var topicPartition = new TopicPartition(topic, 0);
                var offsets = adminClient.listConsumerGroupOffsets(GROUP_ID)
                        .partitionsToOffsetAndMetadata().get();
                var metadata = offsets.get(topicPartition);
                return metadata != null ? Optional.of(metadata.offset()) : Optional.empty();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });
    }
}

