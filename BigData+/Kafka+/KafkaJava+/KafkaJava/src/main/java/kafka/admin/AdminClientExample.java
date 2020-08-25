package kafka.admin;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.DeleteTopicsResult;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;

import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import static java.util.Collections.singletonList;

/**
 * Use AdminClient API.
 */
public class AdminClientExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Properties properties = new Properties();
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        String topic = "admin_client_example";

        AdminClient adminClient = AdminClient.create(properties);
        if (isTopicExist(adminClient, topic)) {
            deleteTopic(adminClient, topic);
        }
        createTopic(adminClient, topic);
        boolean topicExists = isTopicExist(adminClient, topic);
        assert topicExists;
        deleteTopic(adminClient, topic);
    }

    public static void createTopic(AdminClient adminClient, String topic) throws ExecutionException, InterruptedException {
        NewTopic newTopic = new NewTopic(topic, 1, (short) 1);
        CreateTopicsResult result = adminClient.createTopics(singletonList(newTopic));
        result.all().get();
    }

    public static void deleteTopic(AdminClient adminClient, String topic) throws ExecutionException, InterruptedException {
        DeleteTopicsResult result = adminClient.deleteTopics(singletonList(topic));
        result.all().get();
    }

    public static boolean isTopicExist(AdminClient adminClient, String topic) throws ExecutionException, InterruptedException {
        ListTopicsResult result = adminClient.listTopics();
        Set<String> topicNames = result.names().get();
        return topicNames.contains(topic);
    }
}