package kafka.admin.acl;

import org.apache.kafka.clients.admin.Admin;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.SslConfigs;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.nio.file.Files;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import static java.net.http.HttpResponse.BodyHandlers.ofFile;
import static java.nio.file.StandardOpenOption.WRITE;
import static java.util.Collections.singletonList;

/**
 * Working with ACL against "fast-data-dev" Kafka cluster
 * (running the cluster: BigData+/Kafka+/KafkaDocker+/fast-data-dev/fast-data-dev.md)
 * <p>
 * Run cluster: "docker run --rm --net=host -e ENABLE_SSL=1 -e DEBUG=1 lensesio/fast-data-dev"
 */
class AclFastDevDataTest {
    private static final Random RANDOM = new Random();
    private static final String TOPIC = "acl-fast-dev-test-" + RANDOM.nextInt(Integer.MAX_VALUE);

    @Test
    @Timeout(10)
    void createAcl() throws ExecutionException, InterruptedException, IOException, URISyntaxException {
        System.out.println("Topic: " + TOPIC);

        var truststorePath = Files.createTempFile("truststore", ".jks");
        var truststoreBodyHandler = ofFile(truststorePath, WRITE);
        var truststoreRequest = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:3030/certs/truststore.jks"))
                .GET().build();

        var keystorePath = Files.createTempFile("keystore", ".jks");
        var keystoreBodyHandler = ofFile(keystorePath, WRITE);
        var keystoreRequest = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:3030/certs/client.jks"))
                .GET().build();

        var httpClient = HttpClient.newHttpClient();
        httpClient.send(truststoreRequest, truststoreBodyHandler).body();
        httpClient.send(keystoreRequest, keystoreBodyHandler).body();

        var password = "fastdata";
        var adminConfigOverrides = new Properties();
        adminConfigOverrides.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9093");
        adminConfigOverrides.put(AdminClientConfig.SECURITY_PROTOCOL_CONFIG, "SSL");
        adminConfigOverrides.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, keystorePath.toString());
        adminConfigOverrides.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, truststorePath.toString());
        adminConfigOverrides.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, password);
        adminConfigOverrides.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, password);
        adminConfigOverrides.put(SslConfigs.SSL_KEY_PASSWORD_CONFIG, password);

        try (Admin adminClient = AdminClient.create(adminConfigOverrides)) {
            adminClient.createTopics(singletonList(new NewTopic(TOPIC, 1, (short) 1))).all().get();

//            ResourcePattern pattern = new ResourcePattern(ResourceType.TOPIC, TOPIC, PatternType.LITERAL);
//            String principal = "User:John";
//            String host = "localhost";
//            AccessControlEntry entry = new AccessControlEntry(principal, host, AclOperation.ALL, AclPermissionType.ALLOW);
//            AclBinding binding = new AclBinding(pattern, entry);
//            CreateAclsResult aclsResult = adminClient.createAcls(singletonList(binding));
//            aclsResult.all().get();
//            assertThat(topicDescription.name(), equalTo(topic));
//            assertThat(topicDescription.partitions(), hasSize(1));
        }
    }

}
