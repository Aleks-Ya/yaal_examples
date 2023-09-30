package gptui;

import gptui.gpt.GptApi;

import java.util.HashMap;
import java.util.Map;

public class MockGptApi implements GptApi {
    private final Map<String, String> contentSubstringToResponseMap = new HashMap<>();

    @Override
    public String send(String content) {
        return contentSubstringToResponseMap.entrySet().stream()
                .filter(entry -> content.toLowerCase().contains(entry.getKey().toLowerCase()))
                .findFirst()
                .orElseThrow()
                .getValue();
    }

    public MockGptApi put(String contentSubstring, String response) {
        contentSubstringToResponseMap.put(contentSubstring, response);
        return this;
    }
}
