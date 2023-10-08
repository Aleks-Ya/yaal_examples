package gptui.gpt;

import javafx.application.Platform;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class MockGptApi implements GptApi {
    private final Map<String, ResponseInfo> contentSubstringToResponseMap = new HashMap<>();

    @Override
    public String send(String content) {
        if (Platform.isFxApplicationThread()) {
            throw new IllegalStateException("Should not run in the JavaFX Application Thread");
        }
        var info = contentSubstringToResponseMap.entrySet().stream()
                .filter(entry -> content.toLowerCase().contains(entry.getKey().toLowerCase()))
                .findFirst()
                .orElseThrow()
                .getValue();
        try {
            Thread.sleep(info.timeout().toMillis());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return info.content();
    }

    public MockGptApi put(String contentSubstring, String response) {
        contentSubstringToResponseMap.put(contentSubstring, new ResponseInfo(response, Duration.ZERO));
        return this;
    }

    public MockGptApi put(String contentSubstring, String response, Duration timeout) {
        contentSubstringToResponseMap.put(contentSubstring, new ResponseInfo(response, timeout));
        return this;
    }

    record ResponseInfo(String content, Duration timeout) {
    }
}
