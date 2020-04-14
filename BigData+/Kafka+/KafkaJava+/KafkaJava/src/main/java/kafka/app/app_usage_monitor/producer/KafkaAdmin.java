package kafka.app.app_usage_monitor.producer;

import org.apache.kafka.clients.admin.Admin;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;

import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

class KafkaAdmin implements AutoCloseable {
    private final Admin adminClient;

    public KafkaAdmin(String bootstrapServers) {
        Properties adminClientConfig = new Properties();
        adminClientConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        adminClientConfig.put(ConsumerConfig.CLIENT_ID_CONFIG, KafkaAdmin.class.getSimpleName());
        adminClient = AdminClient.create(adminClientConfig);
    }

    public void createTopicIfNotExists(String topicName, int numPartitions) throws ExecutionException, InterruptedException {
        boolean isExists = adminClient.listTopics().names().get().contains(topicName);
        if (!isExists) {
            NewTopic newTopic = new NewTopic(topicName, numPartitions, (short) 1);
            CreateTopicsResult result = adminClient.createTopics(Collections.singletonList(newTopic));
            result.all().get();
        }
    }

    @Override
    public void close() {
        adminClient.close();
    }
}
