package kafka.local.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class KafkaTopicConfig {

    private final String topic;

    KafkaTopicConfig(@Value("${topic}") String topic) {
        this.topic = topic;
    }

    @Bean
    NewTopic topic() {
        return new NewTopic(topic, 1, (short) 1);
    }
}
