package gptui.gpt.gcp;

import java.util.List;

record ResponseBody(List<Candidate> candidates) {
    public record Candidate(String output) {
    }
}
