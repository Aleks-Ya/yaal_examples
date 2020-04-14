package kafka.admin;

import kafka.api.IntegrationTestHarness;
import org.apache.kafka.clients.admin.Admin;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.DescribeTopicsResult;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicDescription;
import org.junit.Test;

import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import static java.lang.String.format;
import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThrows;

/**
 * Use AdminClient API.
 */
public class AdminClientTest extends IntegrationTestHarness {
    private static final String topic = AdminClientTest.class.getSimpleName().toLowerCase();

    @Test(timeout = 10_000)
    public void describeTopics() throws ExecutionException, InterruptedException {
        createTopic(topic, 1, 1, new Properties());

        try (Admin adminClient = createAdminClient(new Properties())) {
            DescribeTopicsResult describeTopicsResult = adminClient.describeTopics(singletonList(topic));
            Map<String, TopicDescription> topicDescriptionMap = describeTopicsResult.all().get();
            TopicDescription topicDescription = topicDescriptionMap.get(topic);
            assertThat(topicDescription.name(), equalTo(topic));
            assertThat(topicDescription.partitions(), hasSize(1));
        }
    }

    @Test(timeout = 10_000)
    public void isTopicExist() throws ExecutionException, InterruptedException {
        String topicExists = "topic_exists";
        String topicNotExists = "topic_not_exists";
        createTopic(topicExists, 1, 1, new Properties());

        try (Admin adminClient = createAdminClient(new Properties())) {
            ListTopicsResult result = adminClient.listTopics();
            Set<String> topicNames = result.names().get();
            assertThat(topicNames, contains(topicExists));
            assertThat(topicNames, not(contains(topicNotExists)));
        }
    }

    @Test(timeout = 10_000)
    public void createTopic() throws ExecutionException, InterruptedException {
        try (Admin adminClient = createAdminClient(new Properties())) {
            Set<String> topicNamesBefore = adminClient.listTopics().names().get();
            assertThat(topicNamesBefore, not(contains(topic)));

            NewTopic newTopic = new NewTopic(topic, 1, (short) 1);
            CreateTopicsResult result = adminClient.createTopics(singletonList(newTopic));
            result.all().get();

            Set<String> topicNamesAfter = adminClient.listTopics().names().get();
            assertThat(topicNamesAfter, contains(topic));
        }
    }

    @Test
    public void createTopicAlreadyExists() {
        createTopic(topic, 1, 1, new Properties());

        String message = format("Topic '%s' already exists.", topic);
        assertThrows(message, ExecutionException.class, () -> {
            try (Admin adminClient = createAdminClient(new Properties())) {
                NewTopic newTopic = new NewTopic(topic, 1, (short) 1);
                adminClient.createTopics(singletonList(newTopic)).all().get();
            }
        });
    }

    @Override
    public int brokerCount() {
        return 1;
    }
}