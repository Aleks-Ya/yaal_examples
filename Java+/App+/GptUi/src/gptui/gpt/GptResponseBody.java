package gptui.gpt;

import java.util.List;

record GptResponseBody(String model, List<Choice> choices) {
    public record Choice(Integer index, GptMessage message, String finish_reason) {
    }
}
