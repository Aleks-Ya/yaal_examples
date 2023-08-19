package subtitles.anki;

import java.util.List;

public record AnkiNoteInfoResponse(List<AnkiNote> result, String error) {
}
