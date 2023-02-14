package actuator.kafka.local;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
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
@ContextConfiguration(classes = {KafkaListenerAnnotationConsumer.class, KafkaConsumerConfig.class})
@TestPropertySource(properties = {
        "topic=topic1",
        "group=groupTestPropertySource",
        "kafka.bootstrapAddress=${spring.embedded.kafka.brokers}"})
class LocalKafkaHealthIndicatorTest {

    @Autowired
    private KafkaListenerAnnotationConsumer consumer;
    @Value("${topic}")
    private String topic;
    @Autowired
    private EmbeddedKafkaBroker broker;
    @Autowired
    private KafkaListenerEndpointRegistry registry;

    @Test
    void test() {
        var listenerContainer = registry.getAllListenerContainers().iterator().next();
        var groupId = listenerContainer.getGroupId();
        assertThat(groupId).isEqualTo("groupTestPropertySource");

        var value1 = "abc";
        var value2 = "xyz";

        var pf = new DefaultKafkaProducerFactory<Integer, String>(KafkaTestUtils.producerProps(broker));
        var template = new KafkaTemplate<>(pf);
        template.setDefaultTopic(topic);
        template.sendDefault(value1);
        template.sendDefault(value2);

        var expGroupPrefix = "groupTestPropertySource: ";
        await().timeout(15, TimeUnit.SECONDS)
                .untilAsserted(() -> assertThat(consumer.getMessages())
                        .contains(expGroupPrefix + value1, expGroupPrefix + value2));
    }
}

