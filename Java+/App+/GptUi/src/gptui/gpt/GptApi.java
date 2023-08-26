package gptui.gpt;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class GptApi {
    private static final String MODEL = "gpt-4";
    private static final Gson gson = new Gson();
    private static final HttpClient httpClient = HttpClient.newHttpClient();
    private static final URI endpoint = URI.create("https://api.openai.com/v1/chat/completions");
    private final String token;

    public GptApi() {
        try {
            token = Files.readString(Path.of(System.getProperty("user.home"), ".gpt/token.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String send(String content) {
        try {
            System.out.println("Sending question: " + content);
            var body = new GptRequestBody(MODEL, List.of(new GptMessage("user", content)), 0.7);
            var json = gson.toJson(body);
            var request = HttpRequest.newBuilder()
                    .uri(endpoint)
                    .header("Authorization", "Bearer " + token)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            var responseBody = gson.fromJson(response.body(), GptResponseBody.class);
            return responseBody.choices().get(0).message().content();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
