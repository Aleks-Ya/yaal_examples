package gptui.gpt.openai;

import java.math.BigDecimal;

public interface GptApi {
    String send(String content, BigDecimal temperature);
}
