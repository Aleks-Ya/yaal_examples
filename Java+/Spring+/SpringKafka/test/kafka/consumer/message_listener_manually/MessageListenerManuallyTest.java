package kafka.consumer.message_listener_manually;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.MessageListenerContainer;
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
@ContextConfiguration(classes = {MessageListenerManuallyTest.class, KafkaMessageListener.class, KafkaConsumerConfig.class})
@TestPropertySource(properties = "topic=topic1")
class MessageListenerManuallyTest {

    @Autowired
    private KafkaMessageListener consumer;
    @Value("${topic}")
    private String topic;
    @Autowired
    private EmbeddedKafkaBroker broker;
    @Autowired
    private KafkaListenerEndpointRegistry registry;
    @Autowired
    private MessageListenerContainer container;

    @Test
    void test() {
        var listenerContainer = registry.getAllListenerContainers().iterator().next();
        var groupId = listenerContainer.getGroupId();
        assertThat(groupId).isEqualTo("groupTest");

        var value1 = "abc";
        var value2 = "xyz";

        var producerProps = KafkaTestUtils.producerProps(broker);
        var pf = new DefaultKafkaProducerFactory<Integer, String>(producerProps);
        var template = new KafkaTemplate<>(pf);
        template.setDefaultTopic(topic);
        template.sendDefault(value1);
        template.sendDefault(value2);

        await().timeout(15, TimeUnit.SECONDS)
                .untilAsserted(() -> assertThat(consumer.getMessages()).contains(value1, value2));
    }

    @Bean
    @Primary
    ConsumerProperties consumerPropertiesTest() {
        return new ConsumerProperties(Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, broker.getBrokersAsString(),
                ConsumerConfig.GROUP_ID_CONFIG, "groupTest",
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest",
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class));
    }
}

