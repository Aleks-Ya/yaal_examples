package kafka.admin;

import kafka.api.IntegrationTestHarness;
import org.apache.kafka.clients.admin.Admin;
import org.apache.kafka.clients.admin.DescribeTopicsResult;
import org.apache.kafka.clients.admin.TopicDescription;
import org.junit.Test;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

/**
 * Use AdminClient API.
 */
public class AdminClientTest extends IntegrationTestHarness {
    private static final String topic = AdminClientTest.class.getSimpleName().toLowerCase();

    @Test(timeout = 10_000)
    public void describeTopics() throws ExecutionException, InterruptedException {
        createTopic(topic, 1, 1, new Properties());

        Admin adminClient = createAdminClient(new Properties());
        DescribeTopicsResult describeTopicsResult = adminClient.describeTopics(singletonList(topic));
        Map<String, TopicDescription> topicDescriptionMap = describeTopicsResult.all().get();
        TopicDescription topicDescription = topicDescriptionMap.get(topic);
        assertThat(topicDescription.name(), equalTo(topic));
        assertThat(topicDescription.partitions(), hasSize(1));
    }

    @Override
    public int brokerCount() {
        return 1;
    }
}