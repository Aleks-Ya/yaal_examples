package gptui.gpt.openai;

import gptui.gpt.gcp.GcpApi;
import javafx.application.Platform;

import javax.inject.Singleton;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Singleton
public class MockGptApi implements GptApi, GcpApi {
    private final Map<RequestInfo, ResponseInfo> contentSubstringToResponseMap = new HashMap<>();

    private final List<String> sendHistory = new ArrayList<>();

    @Override
    public String send(String content, BigDecimal temperature) {
        sendHistory.add(content);
        if (Platform.isFxApplicationThread()) {
            throw new IllegalStateException("Should not run in the JavaFX Application Thread");
        }
        var info = contentSubstringToResponseMap.entrySet().stream()
                .filter(entry -> {
                    var contains = entry.getKey().containsOpt
                            .map(value -> content.toLowerCase().contains(value.toLowerCase()))
                            .orElse(false);
                    var notContains = entry.getKey().notContainOpt
                            .map(value -> !content.toLowerCase().contains(value.toLowerCase()))
                            .orElse(true);
                    return contains && notContains;
                })
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
        return put("has grammatical mistakes", null, response, timeout);
    }

    public MockGptApi putShortResponse(String response, Duration timeout) {
        return put("a short response", null, response, timeout);
    }

    @SuppressWarnings("UnusedReturnValue")
    public MockGptApi putLongResponse(String response, Duration timeout) {
        return put("I will ask you a question about", "a short response", response, timeout);
    }

    @SuppressWarnings("UnusedReturnValue")
    public MockGptApi putGcpResponse(String response, Duration timeout) {
        return put("Answer question about", null, response, timeout);
    }

    @SuppressWarnings("UnusedReturnValue")
    public MockGptApi putFactResponse(String response, Duration timeout) {
        return put("factually correct", null, response, timeout);
    }

    private MockGptApi put(String containsSubstring, String notContainSubstring, String response, Duration timeout) {
        var requestInfo = new RequestInfo(Optional.ofNullable(containsSubstring), Optional.ofNullable(notContainSubstring));
        var responseInfo = new ResponseInfo(response, timeout);
        contentSubstringToResponseMap.put(requestInfo, responseInfo);
        return this;
    }

    public MockGptApi clear() {
        contentSubstringToResponseMap.clear();
        sendHistory.clear();
        return this;
    }

    record RequestInfo(Optional<String> containsOpt, Optional<String> notContainOpt) {
    }

    record ResponseInfo(String content, Duration timeout) {
    }
}
