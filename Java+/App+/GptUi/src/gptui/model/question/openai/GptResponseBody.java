package gptui.model.question.openai;

import java.util.List;

record GptResponseBody(String model, List<Outputs> output, Error error) {
    public record Content(String text) {}

    public record Outputs(List<Content> content, String status) {
    }

    record Error(String message, String type, String param, String code) {
    }
}
