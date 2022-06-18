package kafka.consumer.kafka_listener_annotation.error.kafka_listener_error_handler;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static kafka.consumer.kafka_listener_annotation.error.kafka_listener_error_handler.ConsumerPropertiesConfig.ERROR_HANDLER_BEAN_NAME;

@Component
class KafkaListenerConsumer {
    private final List<Person> persons = new ArrayList<>();
    private final List<Person> skippedPersons = new ArrayList<>();

    @KafkaListener(topics = "${topic}", errorHandler = ERROR_HANDLER_BEAN_NAME)
    private void listen(Person person) {
        System.out.println("Received Message: " + person);
        if (person.id() == 2L) {
            skippedPersons.add(person);
            throw new IllegalArgumentException("Not allowed: " + person);
        }
        persons.add(person);
    }

    public List<Person> getPersons() {
        return persons;
    }

    public List<Person> getSkippedPersons() {
        return skippedPersons;
    }
}
