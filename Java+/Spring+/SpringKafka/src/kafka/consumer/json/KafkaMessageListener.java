package kafka.consumer.json;

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
        System.out.println("Received Message: " + data.value());
        persons.add(data.value());
    }
}
