package sardine;

import com.github.sardine.Sardine;
import com.github.sardine.SardineFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.org.apache.commons.lang3.concurrent.ConcurrentException;
import org.testcontainers.shaded.org.apache.commons.lang3.concurrent.LazyInitializer;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;

@Testcontainers
abstract class BaseWebDavTest {
    protected static final String USERNAME = "user1";
    protected static final String PASSWORD = "pass1";
    private static final int INNER_PORT = 80;
    private static final LazyInitializer<Sardine> sardineInit = new LazyInitializer<>() {
        @Override
        protected Sardine initialize() {
            return SardineFactory.begin(USERNAME, PASSWORD);
        }
    };
    @Container
    @SuppressWarnings("resource")
    private final GenericContainer<?> webdav =
            new GenericContainer<>(DockerImageName.parse("bytemark/webdav:2.4"))
                    .withExposedPorts(INNER_PORT)
                    .withEnv("AUTH_TYPE", "Digest")
                    .withEnv("USERNAME", USERNAME)
                    .withEnv("PASSWORD", PASSWORD);

    protected Sardine newSardineClient() {
        return SardineFactory.begin(USERNAME, PASSWORD);
    }

    protected URI getBaseUri() {
        return URI.create("http://localhost:" + webdav.getMappedPort(INNER_PORT));
    }

    protected void uploadResource(String folder, String file, String content) {
        try {
            var sardine = sardineInit.get();
            var folderUrl = getBaseUri().resolve(folder).toString();
            if (!sardine.exists(folderUrl)) {
                sardine.createDirectory(folderUrl);
            }
            var fileUrl = getBaseUri().toString() + folder + "/" + file;
            sardine.put(fileUrl, content.getBytes(StandardCharsets.UTF_8));
        } catch (IOException | ConcurrentException e) {
            throw new RuntimeException(e);
        }
    }
}
