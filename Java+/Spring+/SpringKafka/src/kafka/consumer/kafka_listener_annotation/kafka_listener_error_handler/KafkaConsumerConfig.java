package kafka.consumer.kafka_listener_annotation.kafka_listener_error_handler;

import org.apache.kafka.common.serialization.StringDeserializer;
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
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
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
                new ErrorHandlingDeserializer<>(new StringDeserializer()),
                new ErrorHandlingDeserializer<>(new JsonDeserializer<>(Person.class)));
    }

    @Bean
    MessageListenerContainer messageListenerContainer(MessageListener<String, Person> messageListener) {
        var containerProperties = new ContainerProperties(topic);
        containerProperties.setMessageListener(messageListener);
        return new KafkaMessageListenerContainer<>(consumerFactory(), containerProperties);
    }

//    @Bean
//    public JsonMessageConverter jsonMessageConverter() {
//        return new ByteArrayJsonMessageConverter();
//    }

//    @Bean
//    public CommonLoggingErrorHandler errorHandler() {
//        return new CommonLoggingErrorHandler();
//    }

//    @Bean
//    public ConsumerAwareListenerErrorHandler listen3ErrorHandler() {
//        return (m, e, c) -> {
////            this.listen3Exception = e;
//            MessageHeaders headers = m.getHeaders();
//            c.seek(new org.apache.kafka.common.TopicPartition(
//                            headers.get(KafkaHeaders.RECEIVED_TOPIC, String.class),
//                            headers.get(KafkaHeaders.RECEIVED_PARTITION_ID, Integer.class)),
//                    headers.get(KafkaHeaders.OFFSET, Long.class));
//            return null;
//        };
//    }
}
