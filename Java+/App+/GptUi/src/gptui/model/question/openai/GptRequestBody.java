package gptui.model.question.openai;

import java.math.BigDecimal;
import java.util.List;

record GptRequestBody(String model, List<GptMessage> messages, BigDecimal temperature) {
}
