package kafka.local.consumer;

import org.springframework.context.annotation.DependsOn;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@DependsOn("KafkaTemplateProducer")
public class KafkaListenerConsumer {
    private final List<String> messages = new ArrayList<>();

    @KafkaListener(topics = "${topic}", groupId = "${group}")
    public void listen(String message) {
        System.out.println("Received Message: " + message);
        messages.add(message);
    }

    public List<String> getMessages() {
        return messages;
    }
}
