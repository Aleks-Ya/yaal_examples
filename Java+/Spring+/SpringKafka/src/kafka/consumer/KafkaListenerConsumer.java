package kafka.consumer;

import org.springframework.context.annotation.DependsOn;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@DependsOn("KafkaTemplateProducer")
public class KafkaListenerConsumer {
    @KafkaListener(topics = "${topic}", groupId = "${group}")
    public void listen(String message) {
        System.out.println("Received Message in group foo: " + message);
    }
}
