package kafka.consumer.kafka_listener_annotation.json.time;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
class KafkaListenerConsumer {
    private final List<Person> persons = new ArrayList<>();

    @KafkaListener(topics = "${topic}")
    private void listen(Person message) {
        System.out.println("Received Message: " + message);
        persons.add(message);
    }

    public List<Person> getPersons() {
        return persons;
    }
}
