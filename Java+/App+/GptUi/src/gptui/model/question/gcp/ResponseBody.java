package gptui.model.question.gcp;

import java.util.List;

record ResponseBody(List<Candidate> candidates) {
    record Candidate(Content content, FinishReason finishReason) {
    }
    @SuppressWarnings("unused")
    enum FinishReason {
        FINISH_REASON_UNSPECIFIED,
        STOP,
        MAX_TOKENS,
        SAFETY,
        RECITATION,
        OTHER
    }
}
