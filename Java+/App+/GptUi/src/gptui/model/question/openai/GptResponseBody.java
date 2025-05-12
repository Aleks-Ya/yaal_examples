package gptui.model.question.openai;

import java.util.List;


record GptResponseBody(String model, List<Choice> choices, Error error) {
    record GptMessage(String role, String content) {
    }

    record Choice(Integer index, GptMessage message, String finish_reason) {
    }

    record Error(String message, String type, String param, String code) {
    }
}
