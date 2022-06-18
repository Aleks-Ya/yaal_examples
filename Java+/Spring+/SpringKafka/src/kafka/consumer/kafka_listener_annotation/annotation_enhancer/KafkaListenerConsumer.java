package kafka.consumer.kafka_listener_annotation.annotation_enhancer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
class KafkaListenerConsumer {
    private final List<String> messages = new ArrayList<>();

    @KafkaListener(topics = "${topic}")
    private void listen(String message) {
        System.out.println("Received Message: " + message);
        messages.add(message);
    }

    public List<String> getMessages() {
        return messages;
    }
}
