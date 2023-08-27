package gptui.gpt;

import com.google.gson.Gson;
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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GptApi {
    private static final Logger log = LoggerFactory.getLogger(GptApi.class);
    private static final String MODEL = "gpt-4";
    private static final Gson gson = new Gson();
    private static final ExecutorService executor = Executors.newFixedThreadPool(3, r -> {
        var thread = new Thread(r);
        thread.setDaemon(true);
        return thread;
    });
    private static final URI endpoint = URI.create("https://api.openai.com/v1/chat/completions");
    private final String token;

    public GptApi() {
        try {
            token = Files.readString(Path.of(System.getProperty("user.home"), ".gpt/token.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public CompletableFuture<String> send(String content) {
        try {
            log.info("Sending question: {}", content);
            var body = new GptRequestBody(MODEL, List.of(new GptMessage("user", content)), 0.7);
            var json = gson.toJson(body);
            var request = HttpRequest.newBuilder()
                    .uri(endpoint)
                    .header("Authorization", "Bearer " + token)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .timeout(Duration.ofMinutes(1))
                    .build();
            return HttpClient.newBuilder().executor(executor).build()
                    .sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(response -> {
                        var responseBody = gson.fromJson(response.body(), GptResponseBody.class);
                        return responseBody.choices().get(0).message().content();
                    });
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
