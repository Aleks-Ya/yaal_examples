package gptui.model.question.gcp;

import com.google.gson.Gson;
import gptui.model.config.ConfigModel;
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
import java.util.List;

import static gptui.model.question.gcp.ResponseBody.FinishReason.STOP;
import static java.math.RoundingMode.HALF_UP;

@Singleton
class GcpApiImpl implements GcpApi {
    private static final Logger log = LoggerFactory.getLogger(GcpApiImpl.class);
    private static final Gson gson = new Gson();
    private static final URI endpoint = URI.create(
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-pro-preview-05-06:generateContent");
    private final String apiKey;

    @Inject
    public GcpApiImpl(ConfigModel configModel) {
        apiKey = configModel.getProperty("gcp.api.key");
    }

    @Override
    public String send(String content, Integer temperature) {
        log.info("Sending question: {}", content);
        try (var client = HttpClient.newHttpClient()) {
            var bigDecimalTemperature = convertTemperature(temperature);
            var body = new RequestBody(List.of(new Content(List.of(new Content.Part(content)), "user")),
                    new RequestBody.GenerationConfig(bigDecimalTemperature, 1));
            var json = gson.toJson(body);
            log.trace("Request body: {}", json);
            var request = HttpRequest.newBuilder()
                    .uri(endpoint)
                    .header("X-goog-api-key", apiKey)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .timeout(Duration.ofMinutes(1))
                    .build();
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                var responseBody = gson.fromJson(response.body(), ResponseBody.class);
                var candidate = responseBody.candidates().getFirst();
                if (candidate.finishReason() != STOP) {
                    var message = String.format("Wrong finish reason in candidate: %s", candidate);
                    throw new RuntimeException(message);
                }
                return candidate.content().parts().getFirst().text();
            } else {
                log.error("GCP API error status {}: {}", response.statusCode(), response.body());
                throw new RuntimeException(response.body());
            }
        } catch (RuntimeException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private BigDecimal convertTemperature(Integer temperature) {
        return BigDecimal.valueOf(temperature).setScale(1, HALF_UP).divide(BigDecimal.valueOf(100), HALF_UP);
    }
}
