package gptui.model.question.gcp;

import java.math.BigDecimal;
import java.util.List;

record RequestBody(List<Content> contents, GenerationConfig generationConfig) {
    record GenerationConfig(BigDecimal temperature, Integer candidateCount) {
    }
}
