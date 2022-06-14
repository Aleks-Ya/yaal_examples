package kafka.local.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class KafkaListenerAnnotationConsumer {
    private final List<String> messages = new ArrayList<>();

    @KafkaListener(topics = "${topic}", groupId = "${group}")
    private void listen(String message) {
        System.out.println("Received Message: " + message);
        messages.add(message);
    }

    public List<String> getMessages() {
        return messages;
    }
}
