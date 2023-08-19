package subtitles.anki;

import java.util.List;

public record AnkiFindNotesResponse(List<Long> result, String error) {
}
