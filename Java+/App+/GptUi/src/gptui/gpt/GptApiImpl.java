package gptui.gpt;

import com.google.gson.Gson;
import gptui.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.List;

public class GptApiImpl implements GptApi {
    private static final Logger log = LoggerFactory.getLogger(GptApiImpl.class);
    private static final String MODEL = "gpt-4";
    private static final Gson gson = new Gson();
    private static final URI endpoint = URI.create("https://api.openai.com/v1/chat/completions");
    private final String token;

    public GptApiImpl() {
        try {
            var config = Configuration.getInstance();
            token = Files.readString(Path.of(config.getAppDir().toString(), "token.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String send(String content) {
        log.info("Sending question: {}", content);
        var body = new GptRequestBody(MODEL, List.of(new GptMessage("user", content)), 0.7);
        var json = gson.toJson(body);
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
            return responseBody.choices().get(0).message().content();
        } else {
            log.error("GPT API error status {}: {}", response.statusCode(), response.body());
            throw new RuntimeException(response.body());
        }
    }
}
