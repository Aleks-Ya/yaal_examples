package anki.connect.response;

import java.util.List;

public record FindNotesResponse(List<Long> result, String error) implements AnkiResponse {
}
