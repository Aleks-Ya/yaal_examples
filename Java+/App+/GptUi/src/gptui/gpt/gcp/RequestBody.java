package gptui.gpt.gcp;

import java.math.BigDecimal;

record RequestBody(RequestPrompt prompt, BigDecimal temperature, Integer candidateCount) {
}
