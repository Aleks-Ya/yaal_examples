package kafka.consumer.message_listener_interface.error.common_error_handler;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
class KafkaMessageListener implements MessageListener<String, Person> {
    private final List<Person> persons = new ArrayList<>();

    public List<Person> getPersons() {
        return persons;
    }

    @Override
    public void onMessage(ConsumerRecord<String, Person> data) {
        var person = data.value();
        System.out.println("Received Message: " + person);
        if (person.id() == 2L) {
            throw new IllegalArgumentException("Not allowed: " + person);
        }
        persons.add(person);
    }
}
