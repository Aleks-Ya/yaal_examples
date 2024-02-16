package gptui.model.question.openai;

public interface GptApi {
    String send(String content, Integer temperature);
}
