package kafka.consumer.kafka_listener_annotation.error.kafka_listener_error_handler;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Map;

@Configuration
class ConsumerPropertiesConfig {
    static final String ERROR_HANDLER_BEAN_NAME = "kafkaListenerErrorHandler";
    private static final Logger log = LoggerFactory.getLogger(ConsumerPropertiesConfig.class);
    private final String bootstrapAddress;

    ConsumerPropertiesConfig(@Value("${kafka.bootstrapAddress}") String bootstrapAddress) {
        this.bootstrapAddress = bootstrapAddress;
    }

    @Bean
    ConsumerProperties consumerPropertiesProd() {
        return new ConsumerProperties(Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress,
                ConsumerConfig.GROUP_ID_CONFIG, "groupProd",
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest",
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class));
    }

    @Bean(name = ERROR_HANDLER_BEAN_NAME)
    KafkaListenerErrorHandler kafkaListenerErrorHandler() {
        return (msg, ex) -> {
            log.error("Invalid message: {}", msg);
            return "";
        };
    }

}
