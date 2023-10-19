package gptui.gpt.openai;

import java.util.List;

record GptResponseBody(String model, List<Choice> choices, Error error) {
    public record Choice(Integer index, GptMessage message, String finish_reason) {
    }

    public record Error(String message, String type, String param, String code) {
    }
}
