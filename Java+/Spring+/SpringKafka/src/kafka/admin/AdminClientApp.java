package kafka.admin;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

import static java.util.Collections.singletonList;

/**
 * Use AdminClient API.
 * Works with a local Kafka cluster (e.g. "BigData+/Kafka+/KafkaDocker+/fast-data-dev").
 */
public class AdminClientApp {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        var properties = new Properties();
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        var topic = "admin_client_example";

        var adminClient = AdminClient.create(properties);
        if (isTopicExist(adminClient, topic)) {
            deleteTopic(adminClient, topic);
        }
        createTopic(adminClient, topic);
        var topicExists = isTopicExist(adminClient, topic);
        assert topicExists;
        deleteTopic(adminClient, topic);
    }

    private static void createTopic(AdminClient adminClient, String topic) throws ExecutionException, InterruptedException {
        var newTopic = new NewTopic(topic, 1, (short) 1);
        var result = adminClient.createTopics(singletonList(newTopic));
        result.all().get();
    }

    private static void deleteTopic(AdminClient adminClient, String topic) throws ExecutionException, InterruptedException {
        var result = adminClient.deleteTopics(singletonList(topic));
        result.all().get();
    }

    private static boolean isTopicExist(AdminClient adminClient, String topic) throws ExecutionException, InterruptedException {
        var result = adminClient.listTopics();
        var topicNames = result.names().get();
        return topicNames.contains(topic);
    }
}