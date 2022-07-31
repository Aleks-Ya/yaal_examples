package actuator.kafka.local;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
class KafkaListenerAnnotationConsumer {
    private final List<String> messages = new ArrayList<>();

    @KafkaListener(topics = "${topic}", groupId = "${group}")
    private void listen(String message) {
        var groupId = KafkaUtils.getConsumerGroupId();
        System.out.println("Received Message: " + message);
        messages.add(groupId + ": " + message);
    }

    public List<String> getMessages() {
        return messages;
    }
}
