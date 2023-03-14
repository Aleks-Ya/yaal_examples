package generic;

import com.github.sardine.SardineFactory;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
class WebDavTest {
    private static final String USERNAME = "user1";
    private static final String PASSWORD = "pass1";
    private static final int INNER_PORT = 80;
    @Container
    @SuppressWarnings("resource")
    private final GenericContainer<?> webdav =
            new GenericContainer<>(DockerImageName.parse("bytemark/webdav:2.4"))
                    .withExposedPorts(INNER_PORT)
                    .withEnv("AUTH_TYPE", "Digest")
                    .withEnv("USERNAME", USERNAME)
                    .withEnv("PASSWORD", PASSWORD);

    @Test
    void list() throws IOException {
        var port = webdav.getMappedPort(INNER_PORT);
        assertThat(webdav.isRunning()).isTrue();
        var sardine = SardineFactory.begin(USERNAME, PASSWORD);
        var resources = sardine.list("http://localhost:" + port);
        assertThat(resources).hasSize(1);
    }
}
