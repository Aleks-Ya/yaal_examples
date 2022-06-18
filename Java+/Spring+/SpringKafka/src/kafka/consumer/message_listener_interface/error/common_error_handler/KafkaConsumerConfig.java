package kafka.consumer.message_listener_interface.error.common_error_handler;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.CommonLoggingErrorHandler;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

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
    ConsumerFactory<String, Person> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerProperties.consumerProperties(),
                new StringDeserializer(), new JsonDeserializer<>(Person.class));
    }

    @Bean
    MessageListenerContainer messageListenerContainer(MessageListener<String, Person> messageListener) {
        var containerProperties = new ContainerProperties(topic);
        containerProperties.setMessageListener(messageListener);
        var container = new KafkaMessageListenerContainer<>(consumerFactory(), containerProperties);
        container.setCommonErrorHandler(new CommonLoggingErrorHandler());
        return container;
    }
}
