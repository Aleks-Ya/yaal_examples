package kafka.consumer.message_listener_manually;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.listener.MessageListenerContainer;

@EnableKafka
@Configuration
class KafkaConsumerConfig {
    private final ConsumerProperties consumerProperties;
    @Value("${topic}")
    private String topic;

    KafkaConsumerConfig(ConsumerProperties consumerProperties) {
        this.consumerProperties = consumerProperties;
    }

    @Bean
    ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerProperties.consumerProperties());
    }

    @Bean
    MessageListenerContainer messageListenerContainer(MessageListener<String, String> messageListener) {
        var containerProperties = new ContainerProperties(topic);
        containerProperties.setMessageListener(messageListener);
        return new KafkaMessageListenerContainer<>(consumerFactory(), containerProperties);
    }
}
