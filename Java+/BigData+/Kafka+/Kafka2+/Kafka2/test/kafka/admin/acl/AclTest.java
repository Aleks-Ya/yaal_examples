package kafka.admin.acl;

import kafka.api.IntegrationTestHarness;
import org.apache.kafka.common.acl.AccessControlEntry;
import org.apache.kafka.common.acl.AclBinding;
import org.apache.kafka.common.acl.AclOperation;
import org.apache.kafka.common.acl.AclPermissionType;
import org.apache.kafka.common.resource.PatternType;
import org.apache.kafka.common.resource.ResourcePattern;
import org.apache.kafka.common.resource.ResourceType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

import static java.util.Collections.singletonList;

class AclTest extends IntegrationTestHarness {
    private static final int BROKER_COUNT = 1;
    private static final String topic = AclTest.class.getSimpleName().toLowerCase();

    @Test
    @Timeout(10)
    void createAcl() throws ExecutionException, InterruptedException {
        createTopic(topic, 1, 1, new Properties());

        var adminConfigOverrides = new Properties();
        adminConfigOverrides.put("security.protocol", "SSL");
        try (var adminClient = createAdminClient(adminConfigOverrides)) {
            var pattern = new ResourcePattern(ResourceType.TOPIC, topic, PatternType.LITERAL);
            var principal = "User:John";
            var host = "localhost";
            var entry = new AccessControlEntry(principal, host, AclOperation.ALL, AclPermissionType.ALLOW);
            var binding = new AclBinding(pattern, entry);
            var aclsResult = adminClient.createAcls(singletonList(binding));
            aclsResult.all().get();
//            assertThat(topicDescription.name()).isEqualTo(topic));
//            assertThat(topicDescription.partitions()).hasSize(1));
        }
    }

    @Override
    public int brokerCount() {
        return BROKER_COUNT;
    }

    @Override
    public Properties serverConfig() {
        var properties = super.serverConfig();
//        properties.put("authorizer.class.name", "kafka.security.authorizer.AclAuthorizer");
//        properties.put("security.inter.broker.protocol", "SASL_PLAINTEXT");
//        properties.put("inter.broker.listener.name", "PLAINTEXT");
//        properties.put("listener.security.protocol.map", "PLAINTEXT://localhost:9092,SSL:SSL,SASL_PLAINTEXT:SASL_PLAINTEXT,SASL_SSL:SASL_SSL");
        return properties;
    }
}