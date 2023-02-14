package actuator.kafka.local;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.Executors;

@Component
public class KafkaComponent {

    @Autowired
    private KafkaTemplateProducer producer;

    @Autowired
    private KafkaListenerAnnotationConsumer consumer;

    @PostConstruct
    private void postConstruct() {
        Executors.newSingleThreadExecutor().submit(() -> {
            try {
                var groupId = System.getProperty("group");
                var message1 = "abc";
                var message2 = "xyz";
                producer.sendMessage(message1);
                producer.sendMessage(message2);

                var expGroupPrefix = groupId + ": ";
                var expMessages = List.of(expGroupPrefix + message1, expGroupPrefix + message2);
                while (!consumer.getMessages().equals(expMessages)) {
                    //noinspection BusyWait

                    Thread.sleep(500);

                }
                Thread.sleep(100000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
