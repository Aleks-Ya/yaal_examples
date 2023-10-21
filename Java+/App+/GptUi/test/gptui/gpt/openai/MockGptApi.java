package gptui.gpt.openai;

import gptui.gpt.gcp.GcpApi;
import javafx.application.Platform;

import javax.inject.Singleton;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
public class MockGptApi implements GptApi, GcpApi {
    private final Map<String, ResponseInfo> contentSubstringToResponseMap = new HashMap<>();

    private final List<String> sendHistory = new ArrayList<>();

    @Override
    public String send(String content) {
        sendHistory.add(content);
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

    public List<String> getSendHistory() {
        return sendHistory;
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
    public MockGptApi putGcpResponse(String response, Duration timeout) {
        return put("Answer question about", response, timeout);
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
        sendHistory.clear();
        return this;
    }

    record ResponseInfo(String content, Duration timeout) {
    }
}
