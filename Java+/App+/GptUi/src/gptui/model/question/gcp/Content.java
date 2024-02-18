package gptui.model.question.gcp;

import java.util.List;

record Content(List<Part> parts, String role) {
    record Part(String text) {
    }
}
