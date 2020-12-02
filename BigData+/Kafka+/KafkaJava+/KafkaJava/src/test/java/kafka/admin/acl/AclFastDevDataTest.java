package kafka.admin.acl;

import org.apache.kafka.clients.admin.Admin;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.CreateAclsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.acl.AccessControlEntry;
import org.apache.kafka.common.acl.AclBinding;
import org.apache.kafka.common.acl.AclOperation;
import org.apache.kafka.common.acl.AclPermissionType;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.resource.PatternType;
import org.apache.kafka.common.resource.ResourcePattern;
import org.apache.kafka.common.resource.ResourceType;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import static java.net.http.HttpResponse.BodyHandlers.ofFile;
import static java.nio.file.StandardOpenOption.WRITE;
import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

/**
 * Working with ACL against "fast-data-dev" Kafka cluster
 * (running the cluster: BigData+/Kafka+/KafkaDocker+/fast-data-dev/fast-data-dev.md)
 * <p>
 * Run cluster: "docker run --rm --net=host -e ENABLE_SSL=1 -e DEBUG=1 lensesio/fast-data-dev"
 */
public class AclFastDevDataTest {
    private static final Random RANDOM = new Random();
    private static final String TOPIC = "acl-fast-dev-test-" + RANDOM.nextInt(Integer.MAX_VALUE);

    @Test(timeout = 10_000)
    public void createAcl() throws ExecutionException, InterruptedException, IOException, URISyntaxException {
        System.out.println("Topic: " + TOPIC);

        Path truststorePath = Files.createTempFile("truststore", ".jks");
        HttpResponse.BodyHandler<Path> truststoreBodyHandler = ofFile(truststorePath, WRITE);
        HttpRequest truststoreRequest = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:3030/certs/truststore.jks"))
                .GET().build();

        Path keystorePath = Files.createTempFile("keystore", ".jks");
        HttpResponse.BodyHandler<Path> keystoreBodyHandler = ofFile(keystorePath, WRITE);
        HttpRequest keystoreRequest = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:3030/certs/client.jks"))
                .GET().build();

        HttpClient httpClient = HttpClient.newHttpClient();
        httpClient.send(truststoreRequest, truststoreBodyHandler).body();
        httpClient.send(keystoreRequest, keystoreBodyHandler).body();

        String password = "fastdata";
        Properties adminConfigOverrides = new Properties();
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
