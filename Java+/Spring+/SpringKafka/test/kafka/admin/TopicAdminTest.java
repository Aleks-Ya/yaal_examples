package kafka.admin;

import org.apache.kafka.clients.admin.NewTopic;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.ExecutionException;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@EmbeddedKafka
@TestPropertySource(properties = {"kafka.bootstrapAddress=${spring.embedded.kafka.brokers}"})
class TopicAdminTest {
    @Autowired
    private EmbeddedKafkaBroker broker;

    @Test
    void createTopic() {
        var topic = "topic1";

        broker.doWithAdmin(adminClient -> {
            try {
                var newTopic = new NewTopic(topic, 1, (short) 1);
                var result = adminClient.createTopics(singletonList(newTopic));
                result.all().get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });

        var topicNames = broker.doWithAdminFunction(adminClient -> {
            try {
                var result = adminClient.listTopics();
                return result.names().get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });

        assertThat(topicNames).contains(topic);
    }
}

