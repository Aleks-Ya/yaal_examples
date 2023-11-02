package anki.connect.response;

public record LongResultResponse(Long result, String error) implements AnkiResponse {
}
