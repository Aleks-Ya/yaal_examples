package kafka.admin;

import kafka.api.IntegrationTestHarness;
import org.apache.kafka.clients.admin.NewTopic;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

import static java.lang.String.format;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;

/**
 * Use AdminClient API.
 */
class AdminClientTest extends IntegrationTestHarness {
    private static final int BROKER_COUNT = 1;
    private static final String topic = AdminClientTest.class.getSimpleName().toLowerCase();

    @Test
    @Timeout(10)
    void describeTopics() throws ExecutionException, InterruptedException {
        createTopic(topic, 1, 1, new Properties());

        try (var adminClient = createAdminClient(new Properties())) {
            var describeTopicsResult = adminClient.describeTopics(singletonList(topic));
            var topicDescriptionMap = describeTopicsResult.all().get();
            var topicDescription = topicDescriptionMap.get(topic);
            assertThat(topicDescription.name(), equalTo(topic));
            assertThat(topicDescription.partitions(), hasSize(1));
        }
    }

    @Test
    @Timeout(10)
    void isTopicExist() throws ExecutionException, InterruptedException {
        var topicExists = "topic_exists";
        var topicNotExists = "topic_not_exists";
        createTopic(topicExists, 1, 1, new Properties());

        try (var adminClient = createAdminClient(new Properties())) {
            var result = adminClient.listTopics();
            var topicNames = result.names().get();
            assertThat(topicNames, contains(topicExists));
            assertThat(topicNames, not(contains(topicNotExists)));
        }
    }

    @Test
    @Timeout(10)
    void createTopic() throws ExecutionException, InterruptedException {
        try (var adminClient = createAdminClient(new Properties())) {
            var topicNamesBefore = adminClient.listTopics().names().get();
            assertThat(topicNamesBefore, not(contains(topic)));

            var newTopic = new NewTopic(topic, 1, (short) 1);
            var result = adminClient.createTopics(singletonList(newTopic));
            result.all().get();

            var topicNamesAfter = adminClient.listTopics().names().get();
            assertThat(topicNamesAfter, contains(topic));
        }
    }

    @Test
    void createTopicAlreadyExists() {
        createTopic(topic, 1, 1, new Properties());
        var message = format("Topic '%s' already exists.", topic);
        assertThatThrownBy(() -> {
            try (var adminClient = createAdminClient(new Properties())) {
                var newTopic = new NewTopic(topic, 1, (short) 1);
                adminClient.createTopics(singletonList(newTopic)).all().get();
            }
        }).isInstanceOf(ExecutionException.class)
                .hasMessageContaining(message);
    }

    @Test
    void getNodes() throws ExecutionException, InterruptedException {
        try (var adminClient = createAdminClient(new Properties())) {
            var describeClusterResult = adminClient.describeCluster();
            var nodesFuture = describeClusterResult.nodes();
            var nodes = nodesFuture.get();
            assertThat(nodes, hasSize(BROKER_COUNT));
        }
    }

    @Override
    public int brokerCount() {
        return BROKER_COUNT;
    }
}