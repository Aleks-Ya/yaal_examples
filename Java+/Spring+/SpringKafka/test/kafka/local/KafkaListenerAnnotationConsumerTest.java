package kafka.local;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
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
@ContextConfiguration(classes = {KafkaListenerAnnotationConsumerTest.class,
        KafkaListenerAnnotationConsumer.class, KafkaConsumerConfig.class})
@TestPropertySource(properties = {"topic=topic1", "group=groupTestPropertySource"})
class KafkaListenerAnnotationConsumerTest {

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

        var producerProps = KafkaTestUtils.producerProps(broker);
        var pf = new DefaultKafkaProducerFactory<Integer, String>(producerProps);
        var template = new KafkaTemplate<>(pf);
        template.setDefaultTopic(topic);
        template.sendDefault(value1);
        template.sendDefault(value2);

        var expGroupPrefix = "groupTestPropertySource: ";
        await().timeout(15, TimeUnit.SECONDS)
                .untilAsserted(() -> assertThat(consumer.getMessages())
                        .contains(expGroupPrefix + value1, expGroupPrefix + value2));
    }

    @Bean
    ConsumerProperties consumerPropertiesTest() {
        return new ConsumerProperties(Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, broker.getBrokersAsString(),
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest",
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class));
    }
}

