package opensearch3;

import org.apache.hc.core5.http.HttpHost;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.opensearch.client.RestClient;
import org.opensearch.client.RestHighLevelClient;
import org.opensearch.testcontainers.OpenSearchContainer;
import org.testcontainers.utility.DockerImageName;

import java.net.URISyntaxException;

public class OpenSearchExtension implements BeforeEachCallback, AfterEachCallback, ParameterResolver {
    public static final DockerImageName OPENSEARCH3 = DockerImageName.parse("opensearchproject/opensearch:3.6.0");
    private OpenSearchContainer<?> container;
    private RestHighLevelClient client;

    @Override
    public void beforeEach(@NonNull ExtensionContext context) throws URISyntaxException {
        container = new OpenSearchContainer<>(OPENSEARCH3);
        container.start();
        var builder = RestClient.builder(HttpHost.create(container.getHttpHostAddress()));
        client = new RestHighLevelClient(builder);
    }

    @Override
    public void afterEach(@NonNull ExtensionContext context) throws Exception {
        if (client != null) {
            client.close();
        }
        if (container != null) {
            container.stop();
        }
    }

    @Override
    public boolean supportsParameter(
            ParameterContext parameterContext,
            @NonNull ExtensionContext extensionContext
    ) throws ParameterResolutionException {
        var type = parameterContext.getParameter().getType();
        return type == RestHighLevelClient.class || type == RestClient.class;
    }

    @Override
    public Object resolveParameter(
            @NonNull ParameterContext parameterContext,
            @NonNull ExtensionContext extensionContext
    ) throws ParameterResolutionException {
        var type = parameterContext.getParameter().getType();
        if (type == RestClient.class) {
            return client.getLowLevelClient();
        }
        return client;
    }
}