package gptui.model.question.openai;

import java.math.BigDecimal;

record GptRequestBody(String model, String input, BigDecimal temperature) {
}
