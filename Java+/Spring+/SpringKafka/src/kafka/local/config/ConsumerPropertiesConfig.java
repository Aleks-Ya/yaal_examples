package kafka.local.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
class ConsumerPropertiesConfig {
    private final String bootstrapAddress;
    private final String groupId;

    ConsumerPropertiesConfig(@Value("${kafka.bootstrapAddress}") String bootstrapAddress,
                             @Value("${group}") String groupId) {
        this.bootstrapAddress = bootstrapAddress;
        this.groupId = groupId;
    }

    @Bean
    ConsumerProperties consumerProperties() {
        return new ConsumerProperties(Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress,
                ConsumerConfig.GROUP_ID_CONFIG, groupId,
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest",
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class));
    }

}
