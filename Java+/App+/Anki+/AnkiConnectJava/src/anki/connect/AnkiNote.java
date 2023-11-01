package anki.connect;

import java.util.List;
import java.util.Map;

public record AnkiNote(Long noteId, List<String> tags, Map<String, AnkiField> fields) {
}

