package gptui.ui.model.question.gcp;

import java.util.List;

record ResponseBody(List<Candidate> candidates) {
    public record Candidate(String output) {
    }
}
