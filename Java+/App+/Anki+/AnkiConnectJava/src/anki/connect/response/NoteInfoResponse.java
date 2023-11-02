package anki.connect.response;

import anki.connect.AnkiNote;

import java.util.List;

public record NoteInfoResponse(List<AnkiNote> result, String error) implements AnkiResponse {
}
