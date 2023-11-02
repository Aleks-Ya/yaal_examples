package anki.connect.request;

import java.util.List;
import java.util.Map;

public record AddNoteFieldsParams(Note note) {
    public record Note(String deckName, String modelName, List<String> tags, Map<String, String> fields,
                       Map<String, Object> options) {
    }
}
