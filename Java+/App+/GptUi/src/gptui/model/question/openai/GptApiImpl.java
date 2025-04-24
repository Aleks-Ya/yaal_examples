package gptui.model.question.openai;

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

import static java.math.RoundingMode.HALF_UP;

@Singleton
class GptApiImpl implements GptApi {
    private static final Logger log = LoggerFactory.getLogger(GptApiImpl.class);
    private static final String MODEL = "gpt-4.1";
    private static final Gson gson = new Gson();
    private static final URI endpoint = URI.create("https://api.openai.com/v1/chat/completions");
    private final String token;

    @Inject
    public GptApiImpl(ConfigModel configModel) {
        token = configModel.getProperty("openai.token");
    }

    @Override
    public String send(String content, Integer temperature) {
        log.info("Sending question: {}", content);
        var bigDecimalTemperature = convertTemperature(temperature);
        var body = new GptRequestBody(MODEL, List.of(new GptMessage("user", content)), bigDecimalTemperature);
        var json = gson.toJson(body);
        log.trace("Request body: {}", json);
        HttpResponse<String> response;
        var request = HttpRequest.newBuilder()
                .uri(endpoint)
                .header("Authorization", "Bearer " + token)
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
            var responseBody = gson.fromJson(response.body(), GptResponseBody.class);
            return responseBody.choices().getFirst().message().content();
        } else {
            log.error("GPT API error status {}: {}", response.statusCode(), response.body());
            throw new RuntimeException(response.body());
        }
    }

    private BigDecimal convertTemperature(Integer temperature) {
        return BigDecimal.valueOf(temperature).setScale(1, HALF_UP).divide(BigDecimal.valueOf(100), HALF_UP);
    }
}
