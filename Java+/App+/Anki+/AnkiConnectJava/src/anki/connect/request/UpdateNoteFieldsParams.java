package anki.connect.request;

import java.util.Map;

public record UpdateNoteFieldsParams(Note note) {
    public record Note(Long id, Map<String, String> fields) {
    }
}
