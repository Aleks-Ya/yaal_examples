package anki.connect;

import anki.connect.request.AnkiRequest;
import anki.connect.response.AnkiResponse;

public class AnkiException extends RuntimeException {
    public AnkiException(AnkiRequest ankiRequest, AnkiResponse ankiResponse) {
        super(String.format("Error during request '%s': '%s'", ankiRequest, ankiResponse.error()));
    }
}
