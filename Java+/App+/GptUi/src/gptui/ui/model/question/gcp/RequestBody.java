package gptui.ui.model.question.gcp;

import java.math.BigDecimal;

record RequestBody(RequestPrompt prompt, BigDecimal temperature, Integer candidateCount) {
}
