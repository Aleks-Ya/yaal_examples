package gptui.model.question.gcp;

public interface GcpApi {
    String send(String content, Integer temperature);
}
