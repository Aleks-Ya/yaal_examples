package gptui.ui.model.question.gcp;

import com.google.gson.Gson;
import gptui.config.Configuration;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static java.math.RoundingMode.HALF_UP;

@Singleton
class GcpApiImpl implements GcpApi {
    private static final Logger log = LoggerFactory.getLogger(GcpApiImpl.class);
    private static final Gson gson = new Gson();
    private static final URI endpoint = URI.create("https://generativelanguage.googleapis.com/v1beta3/models/text-bison-001:generateText");
    private final String apiKey;

    @Inject
    public GcpApiImpl(Configuration configuration) {
        apiKey = configuration.getProperty("gcp.api.key");
    }

    @Override
    public String send(String content, Integer temperature) {
        log.info("Sending question: {}", content);
        var bigDecimalTemperature = convertTemperature(temperature);
        var body = new RequestBody(new RequestPrompt(content), bigDecimalTemperature, 1);
        var json = gson.toJson(body);
        log.trace("Request body: {}", json);
        HttpResponse<String> response;
        var request = HttpRequest.newBuilder()
                .uri(endpoint)
                .header("X-goog-api-key", apiKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .timeout(Duration.ofMinutes(1))
                .build();
        try (var client = HttpClient.newHttpClient()) {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        if (response.statusCode() == 200) {
            var responseBody = gson.fromJson(response.body(), ResponseBody.class);
            return responseBody.candidates().getFirst().output();
        } else {
            log.error("GCP API error status {}: {}", response.statusCode(), response.body());
            throw new RuntimeException(response.body());
        }
    }

    private BigDecimal convertTemperature(Integer temperature) {
        return BigDecimal.valueOf(temperature).setScale(1, HALF_UP).divide(BigDecimal.valueOf(100), HALF_UP);
    }
}
