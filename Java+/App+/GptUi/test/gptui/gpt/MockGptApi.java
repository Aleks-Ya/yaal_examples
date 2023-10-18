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

    public MockGptApi putGrammarResponse(String response, Duration timeout) {
        return put("has grammatical mistakes", response, timeout);
    }

    public MockGptApi putShortResponse(String response, Duration timeout) {
        return put("a short response", response, timeout);
    }

    @SuppressWarnings("UnusedReturnValue")
    public MockGptApi putLongResponse(String response, Duration timeout) {
        return put("a detailed response", response, timeout);
    }

    @SuppressWarnings("UnusedReturnValue")
    public MockGptApi putFactResponse(String response, Duration timeout) {
        return put("factually correct", response, timeout);
    }

    private MockGptApi put(String contentSubstring, String response, Duration timeout) {
        contentSubstringToResponseMap.put(contentSubstring, new ResponseInfo(response, timeout));
        return this;
    }

    public MockGptApi clear() {
        contentSubstringToResponseMap.clear();
        return this;
    }

    record ResponseInfo(String content, Duration timeout) {
    }
}
