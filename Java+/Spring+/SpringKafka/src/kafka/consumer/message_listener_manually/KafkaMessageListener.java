package kafka.consumer.message_listener_manually;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
class KafkaMessageListener implements MessageListener<String, String> {
    private final List<String> messages = new ArrayList<>();

    public List<String> getMessages() {
        return messages;
    }

    @Override
    public void onMessage(ConsumerRecord<String, String> data) {
        System.out.println("Received Message: " + data.value());
        messages.add(data.value());
    }
}
