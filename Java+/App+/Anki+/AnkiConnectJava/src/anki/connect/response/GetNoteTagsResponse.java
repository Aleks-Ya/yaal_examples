package anki.connect.response;

import java.util.List;

public record GetNoteTagsResponse(List<String> result, String error) implements AnkiResponse {
}
