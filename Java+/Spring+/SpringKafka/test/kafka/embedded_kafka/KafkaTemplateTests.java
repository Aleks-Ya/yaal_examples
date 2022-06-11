package kafka.embedded_kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.allOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.kafka.test.assertj.KafkaConditions.key;
import static org.springframework.kafka.test.assertj.KafkaConditions.keyValue;
import static org.springframework.kafka.test.assertj.KafkaConditions.partition;
import static org.springframework.kafka.test.assertj.KafkaConditions.value;

/**
 * Source: https://docs.spring.io/spring-kafka/docs/current/reference/html/#example
 */
@EmbeddedKafka
@SuppressWarnings("JavadocLinkAsPlainText")
public class KafkaTemplateTests {
    @Test
    public void testTemplate(EmbeddedKafkaBroker broker) throws Exception {
        var TEMPLATE_TOPIC = "templateTopic";
        var consumerProps = KafkaTestUtils.consumerProps("testT", "false", broker);
        var cf = new DefaultKafkaConsumerFactory<Integer, String>(consumerProps);
        var containerProperties = new ContainerProperties(TEMPLATE_TOPIC);
        var container = new KafkaMessageListenerContainer<>(cf, containerProperties);
        final BlockingQueue<ConsumerRecord<Integer, String>> records = new LinkedBlockingQueue<>();
        container.setupMessageListener((MessageListener<Integer, String>) record -> {
            System.out.println(record);
            records.add(record);
        });
        container.setBeanName("templateTests");
        container.start();
        ContainerTestUtils.waitForAssignment(container, broker.getPartitionsPerTopic());
        var producerProps = KafkaTestUtils.producerProps(broker);
        ProducerFactory<Integer, String> pf = new DefaultKafkaProducerFactory<>(producerProps);
        var template = new KafkaTemplate<>(pf);
        template.setDefaultTopic(TEMPLATE_TOPIC);
        template.sendDefault("foo");
        assertThat(records.poll(10, TimeUnit.SECONDS)).has(value("foo"));
        template.sendDefault(0, 2, "bar");
        var received = records.poll(10, TimeUnit.SECONDS);
        // using individual assertions
        assertThat(received).has(key(2));
        assertThat(received).has(value("bar"));
        assertThat(received).has(partition(0));
        template.send(TEMPLATE_TOPIC, 0, 2, "baz");
        received = records.poll(10, TimeUnit.SECONDS);
        // using allOf()
        assertThat(received).has(allOf(keyValue(2, "baz"), partition(0)));
    }
}

