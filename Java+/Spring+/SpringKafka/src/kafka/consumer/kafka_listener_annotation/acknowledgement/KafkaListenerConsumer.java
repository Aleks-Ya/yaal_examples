package kafka.consumer.kafka_listener_annotation.acknowledgement;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
class KafkaListenerConsumer {
    private final List<Person> persons = new ArrayList<>();

    @KafkaListener(topics = "${topic}", batch = "true")
    private void listen(List<Person> messages, Acknowledgment acknowledgment) {
        System.out.println("Received Messages: " + messages);
        persons.addAll(messages);
        acknowledgment.acknowledge();
    }

    public List<Person> getPersons() {
        return persons;
    }
}
