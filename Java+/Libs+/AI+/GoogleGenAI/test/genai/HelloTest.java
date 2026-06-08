package genai;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import org.junit.jupiter.api.Test;

class HelloTest {
    @Test
    void hello() {
        try (Client client = Client.builder().apiKey("your-api-key").build()) {
            GenerateContentResponse response =
                    client.models.generateContent("gemini-2.5-flash", "What is your name?", null);
            System.out.println("Unary response: " + response.text());
            response
                    .sdkHttpResponse()
                    .ifPresent(httpResponse ->
                            System.out.println("Response headers: " + httpResponse.headers().orElse(null)));
        }
    }

}
