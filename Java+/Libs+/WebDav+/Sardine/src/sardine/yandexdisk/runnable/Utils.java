package sardine.yandexdisk.runnable;

import io.github.resilience4j.retry.RetryConfig;
import org.apache.http.client.utils.URIBuilder;

import java.net.URISyntaxException;

import static java.time.Duration.ofMinutes;

class Utils {
    static final RetryConfig retryConfig = RetryConfig.custom().maxAttempts(5).waitDuration(ofMinutes(1)).build();
    private static final String WEBDAV_ENDPOINT = "https://webdav.yandex.com";

    public static String makeUrl(String resourcePath) throws URISyntaxException {
        return new URIBuilder(WEBDAV_ENDPOINT).setPath(resourcePath).build().toString();
    }
}
